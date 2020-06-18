package com.marcoscouto.pontointeligente.pontointeligente.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Company(
        val name: String,
        val cnpj: String,
        @Id
        val id: String? = null
)