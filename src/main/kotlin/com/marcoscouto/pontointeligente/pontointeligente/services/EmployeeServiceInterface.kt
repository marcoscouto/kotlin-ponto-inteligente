package com.marcoscouto.pontointeligente.pontointeligente.services

import com.marcoscouto.pontointeligente.pontointeligente.documents.Employee

interface EmployeeServiceInterface {

    fun save(employee: Employee): Employee

    fun findByCpf(cpf: String): Employee?

    fun findByEmail(email: String): Employee?

    fun findById(id: String): Employee?
}