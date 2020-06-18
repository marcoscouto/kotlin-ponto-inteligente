package com.marcoscouto.pontointeligente.pontointeligente.services

import com.marcoscouto.pontointeligente.pontointeligente.documents.Company

interface CompanyServiceInterface {

    fun findByCnpj(cnpj: String): Company?

    fun save(company: Company): Company
}