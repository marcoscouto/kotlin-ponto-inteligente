package com.marcoscouto.pontointeligente.pontointeligente.response

data class Response<T> (
        val errors: ArrayList<String> = arrayListOf(),
        var data: T? = null
)