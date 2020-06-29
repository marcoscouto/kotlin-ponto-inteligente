package com.marcoscouto.pontointeligente.pontointeligente.controllers

import com.marcoscouto.pontointeligente.pontointeligente.documents.Company
import com.marcoscouto.pontointeligente.pontointeligente.documents.Employee
import com.marcoscouto.pontointeligente.pontointeligente.dtos.LegalPersonRegistryDTO
import com.marcoscouto.pontointeligente.pontointeligente.enums.ProfileEnum
import com.marcoscouto.pontointeligente.pontointeligente.response.Response
import com.marcoscouto.pontointeligente.pontointeligente.services.impl.CompanyService
import com.marcoscouto.pontointeligente.pontointeligente.services.impl.EmployeeService
import com.marcoscouto.pontointeligente.pontointeligente.utils.PasswordUtils
import org.springframework.boot.context.properties.bind.BindResult
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/registry-legalperson")
class LegalPersonController(val companyService: CompanyService,
                            val employeeService: EmployeeService) {

    val response: Response<LegalPersonRegistryDTO> = Response()

    @PostMapping
    fun save(@Valid @RequestBody legalPersonRegistryDTO: LegalPersonRegistryDTO,
             result: BindingResult): ResponseEntity<Response<LegalPersonRegistryDTO>> {

        validateData(legalPersonRegistryDTO, result)
        if (result.hasErrors()) {
            for (erro in result.allErrors)
                erro.defaultMessage?.let { response.errors.add(it) }
            return ResponseEntity.badRequest().body(response)
        }

        var company: Company = convertDTOtoCompany(legalPersonRegistryDTO)
        company = companyService.save(company)

        var employee: Employee = convertDTOtoEmployee(legalPersonRegistryDTO, company)
        employee = employeeService.save(employee)
        response.data = convertLegalPersontoDTO(employee, company)

        return ResponseEntity.ok(response)
    }

    private fun convertLegalPersontoDTO(employee: Employee, company: Company): LegalPersonRegistryDTO? {
        return LegalPersonRegistryDTO(employee.name, employee.email, "", employee.cpf, company.cnpj, company.socialReason, employee.id)

    }

    private fun convertDTOtoEmployee(legalPersonRegistryDTO: LegalPersonRegistryDTO, company: Company): Employee {
        return Employee(legalPersonRegistryDTO.name, legalPersonRegistryDTO.email, PasswordUtils().generateBCrypt(legalPersonRegistryDTO.password),
                legalPersonRegistryDTO.cpf, ProfileEnum.ROLE_ADMIN, company.id.toString())

    }

    private fun convertDTOtoCompany(legalPersonRegistryDTO: LegalPersonRegistryDTO): Company {
        return Company(legalPersonRegistryDTO.socialReason, legalPersonRegistryDTO.cnpj)
    }

    private fun validateData(legalPersonRegistryDTO: LegalPersonRegistryDTO, result: BindingResult) {
        val company: Company? = companyService.findByCnpj(legalPersonRegistryDTO.cnpj)
        if (company != null)
            result.addError(ObjectError("Empresa", "Empresa já existente"))

        val employeeCPF: Employee? = employeeService.findByCpf(legalPersonRegistryDTO.cpf)
        if (employeeCPF != null)
            result.addError(ObjectError("Funcionário", "CPF já existente"))

        val employeeEmail: Employee? = employeeService.findByEmail(legalPersonRegistryDTO.email)
        if (employeeEmail != null)
            result.addError(ObjectError("Funcionário", "Email já existente"))

    }

}