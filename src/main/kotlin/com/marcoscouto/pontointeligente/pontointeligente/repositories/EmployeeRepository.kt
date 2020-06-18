package com.marcoscouto.pontointeligente.pontointeligente.repositories

import com.marcoscouto.pontointeligente.pontointeligente.documents.Employee
import org.springframework.data.mongodb.repository.MongoRepository

interface EmployeeRepository : MongoRepository<Employee, String> {

    fun findByEmail(email: String) : Employee?

    fun findByCpf(cpf: String) : Employee?

}