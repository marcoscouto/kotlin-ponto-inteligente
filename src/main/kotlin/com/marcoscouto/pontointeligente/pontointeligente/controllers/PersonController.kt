package com.marcoscouto.pontointeligente.pontointeligente.controllers

import com.marcoscouto.pontointeligente.pontointeligente.documents.Company
import com.marcoscouto.pontointeligente.pontointeligente.documents.Employee
import com.marcoscouto.pontointeligente.pontointeligente.dtos.PersonRegistryDTO
import com.marcoscouto.pontointeligente.pontointeligente.enums.ProfileEnum
import com.marcoscouto.pontointeligente.pontointeligente.response.Response
import com.marcoscouto.pontointeligente.pontointeligente.services.impl.CompanyService
import com.marcoscouto.pontointeligente.pontointeligente.services.impl.EmployeeService
import com.marcoscouto.pontointeligente.pontointeligente.utils.PasswordUtils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/registry-person")
class PersonController(val companyService: CompanyService,
                       val employeeService: EmployeeService) {

    @PostMapping
    fun save(@Valid @RequestBody personRegistryDTO: PersonRegistryDTO, result: BindingResult):
            ResponseEntity<Response<PersonRegistryDTO>> {

        val response: Response<PersonRegistryDTO> = Response()

        var company: Company? = companyService.findByCnpj(personRegistryDTO.cnpj)
        validateData(personRegistryDTO, company, result)

        if (result.hasErrors()) {
            for (erro in result.allErrors)
                erro.defaultMessage?.let { response.errors.add(it) }
            return ResponseEntity.badRequest().body(response)
        }

        var employee: Employee = convertDTOtoEmployee(personRegistryDTO, company!!)

        employee = employeeService.save(employee)
        response.data = convertPersonDTO(employee, company!!)

        return ResponseEntity.ok(response)
    }

    private fun convertPersonDTO(employee: Employee, company: Company): PersonRegistryDTO? {
        return PersonRegistryDTO(employee.name, employee.email, "", employee.cpf,
                company.cnpj, company.id.toString(), employee.valueHour.toString(),
                employee.quantityHoursWorkedDay.toString(), employee.quantityHoursLunch.toString(), employee.id)
    }

    private fun convertDTOtoEmployee(personRegistryDTO: PersonRegistryDTO, company: Company): Employee {
        return Employee(personRegistryDTO.name, personRegistryDTO.email, PasswordUtils().generateBCrypt(personRegistryDTO.password),
                personRegistryDTO.cpf, ProfileEnum.ROLE_USER, company.id.toString(), personRegistryDTO.valueHour?.toDouble(),
                personRegistryDTO.quantityHoursWorkedDay?.toFloat(), personRegistryDTO.quantityHoursLunch?.toFloat(),
                personRegistryDTO.id)
    }

    private fun validateData(personRegistryDTO: PersonRegistryDTO, company: Company?, result: BindingResult) {
        if (company == null)
            result.addError(ObjectError("Empresa", "Empresa não cadastrada"))

        val employeeCPF: Employee? = employeeService.findByCpf(personRegistryDTO.cpf)
        if (employeeCPF != null)
            result.addError(ObjectError("Funcionário", "CPF já existente"))

        val employeeEmail: Employee? = employeeService.findByCpf(personRegistryDTO.email)
        if (employeeEmail != null)
            result.addError(ObjectError("Funcionário", "Email já existente"))
    }

}