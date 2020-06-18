package com.marcoscouto.pontointeligente.pontointeligente.repositories

import com.marcoscouto.pontointeligente.pontointeligente.documents.Company
import org.springframework.data.mongodb.repository.MongoRepository

interface CompanyRepository:  MongoRepository<Company, String> {

    fun findByCnpj(cnpj: String) : Company?

}