package com.marcoscouto.pontointeligente.pontointeligente.controllers

import com.marcoscouto.pontointeligente.pontointeligente.documents.Employee
import com.marcoscouto.pontointeligente.pontointeligente.dtos.EmployeeDTO
import com.marcoscouto.pontointeligente.pontointeligente.response.Response
import com.marcoscouto.pontointeligente.pontointeligente.services.impl.EmployeeService
import com.marcoscouto.pontointeligente.pontointeligente.utils.PasswordUtils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@RequestMapping("/api/employees")
class EmployeeController(val employeeService: EmployeeService) {

    @PutMapping
    fun update(@PathVariable("id") id: String, @Valid @RequestBody employeeDTO: EmployeeDTO, result: BindingResult)
            : ResponseEntity<Response<EmployeeDTO>> {

        val response: Response<EmployeeDTO> = Response()
        val employee: Employee? = employeeService.findById(id)

        if (employee == null) {
            result.addError(ObjectError("Funcionário", "Funcionário não encontrado"))
        }

        val updateEmployeeData: Employee = updateEmployeeData(employee!!, employeeDTO)
        employeeService.save(updateEmployeeData)
        response.data = convertEmployeetoDTO(updateEmployeeData)

        return ResponseEntity.ok(response)
    }

    private fun convertEmployeetoDTO(employee: Employee): EmployeeDTO? {
        return EmployeeDTO(employee.name, employee.email, "", employee.valueHour.toString(),
                employee.quantityHoursWorkedDay.toString(), employee.quantityHoursLunch.toString(), employee.id)
    }

    private fun updateEmployeeData(employee: Employee, employeeDTO: EmployeeDTO): Employee {
        var password: String
        if (employeeDTO.password == null)
            password = employee.password
        else
            password = PasswordUtils().generateBCrypt(employeeDTO.password)


        return Employee(employeeDTO.name, employee.email, password,
                employee.cpf, employee.perfil, employee.companyId, employeeDTO.valueHour?.toDouble(),
                employeeDTO.quantityHoursWorkedDay?.toFloat(),
                employeeDTO.quantityHoursLunch?.toFloat(),
                employee.id)
    }
}