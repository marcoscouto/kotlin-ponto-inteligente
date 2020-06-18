package com.marcoscouto.pontointeligente.pontointeligente.documents

import com.marcoscouto.pontointeligente.pontointeligente.enums.TypeEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class Entry (
        val data: LocalDate,
        val type: TypeEnum,
        val employeeId: String,
        val description: String? = "",
        val localization: String? = "",
        @Id val id: String? = null
)