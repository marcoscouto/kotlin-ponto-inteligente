package com.marcoscouto.pontointeligente.pontointeligente.services

import com.marcoscouto.pontointeligente.pontointeligente.documents.Employee

interface EmployeeServiceInterface {

    fun save(employee: Employee): Employee

    fun findByCpf(employee: Employee): Employee?

    fun findByEmail(employee: Employee): Employee?

    fun findById(employee: Employee): Employee?
}