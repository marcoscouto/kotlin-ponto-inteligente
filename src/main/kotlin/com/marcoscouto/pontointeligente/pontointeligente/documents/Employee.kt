package com.marcoscouto.pontointeligente.pontointeligente.documents

import com.marcoscouto.pontointeligente.pontointeligente.enums.ProfileEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Employee(
        val name: String,
        val email: String,
        val password: String,
        val cpf: String,
        val perfil: ProfileEnum,
        val companyId: String,
        val valueHour: Double? = 0.0,
        val quantityHoursWorkedDay: Float? = 0.0f,
        val quantityHoursLunch: Float? = 0.0f,
        @Id
        val id: String? = null

)