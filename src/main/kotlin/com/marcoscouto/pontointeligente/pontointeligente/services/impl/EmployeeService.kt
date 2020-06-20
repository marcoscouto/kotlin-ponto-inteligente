package com.marcoscouto.pontointeligente.pontointeligente.services.impl

import com.marcoscouto.pontointeligente.pontointeligente.documents.Employee
import com.marcoscouto.pontointeligente.pontointeligente.repositories.EmployeeRepository
import com.marcoscouto.pontointeligente.pontointeligente.services.EmployeeServiceInterface
import org.springframework.stereotype.Service

@Service
class EmployeeService(val repository: EmployeeRepository): EmployeeServiceInterface {

    override fun save(employee: Employee): Employee = repository.save(employee)

    override fun findByCpf(cpf: String): Employee? = repository.findByCpf(cpf)

    override fun findByEmail(email: String): Employee? = repository.findByEmail(email)

    override fun findById(id: String): Employee? = repository.findById(id).get()
}