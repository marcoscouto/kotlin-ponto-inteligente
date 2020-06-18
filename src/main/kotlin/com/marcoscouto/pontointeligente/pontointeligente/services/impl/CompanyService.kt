package com.marcoscouto.pontointeligente.pontointeligente.services.impl

import com.marcoscouto.pontointeligente.pontointeligente.documents.Company
import com.marcoscouto.pontointeligente.pontointeligente.repositories.CompanyRepository
import com.marcoscouto.pontointeligente.pontointeligente.services.CompanyServiceInterface
import org.springframework.stereotype.Service

@Service
class CompanyService(val repository: CompanyRepository) : CompanyServiceInterface {

    override fun findByCnpj(cnpj: String): Company? = repository.findByCnpj(cnpj)

    override fun save(company: Company): Company = repository.save(company)
}