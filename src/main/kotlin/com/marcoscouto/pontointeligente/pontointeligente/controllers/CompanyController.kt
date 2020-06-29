package com.marcoscouto.pontointeligente.pontointeligente.controllers

import com.marcoscouto.pontointeligente.pontointeligente.documents.Company
import com.marcoscouto.pontointeligente.pontointeligente.dtos.CompanyDTO
import com.marcoscouto.pontointeligente.pontointeligente.response.Response
import com.marcoscouto.pontointeligente.pontointeligente.services.impl.CompanyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/companies")
class CompanyController(val companyService: CompanyService) {

    @GetMapping("/cnpj/{cnpj}")
    fun findByCNPJ(@PathVariable("cnpj") cnpj: String): ResponseEntity<Response<CompanyDTO>> {
        val response: Response<CompanyDTO> = Response()
        val company: Company? = companyService.findByCnpj(cnpj)

        if (company == null) {
            response.errors.add("Empresa não encontrada para o CNPJ $cnpj")
            return ResponseEntity.badRequest().body(response)
        }

        response.data = convertCompanytoDTO(company)
        return ResponseEntity.ok(response)
    }

    private fun convertCompanytoDTO(company: Company): CompanyDTO? {
        return CompanyDTO(company.socialReason, company.cnpj, company.id)
    }

}