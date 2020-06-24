package com.marcoscouto.pontointeligente.pontointeligente.dtos

import javax.validation.constraints.NotEmpty

data class EntryDTO(

        @get:NotEmpty(message = "Data não pode ser vazia")
        val date: String? = null,

        @get:NotEmpty(message = "Tipo não pode ser vazio")
        val type: String? = null,

        val description: String? = null,
        val localization: String? = null,
        val employeeId: String? = null,
        val id: String? = null

)