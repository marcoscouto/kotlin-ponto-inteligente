package com.marcoscouto.pontointeligente.pontointeligente.services.impl

import com.marcoscouto.pontointeligente.pontointeligente.documents.Employee
import com.marcoscouto.pontointeligente.pontointeligente.services.EmployeeServiceInterface
import org.springframework.stereotype.Service

@Service
class EmployeeService: EmployeeServiceInterface {

    override fun save(employee: Employee): Employee {
        TODO("Not yet implemented")
    }

    override fun findByCpf(employee: Employee): Employee? {
        TODO("Not yet implemented")
    }

    override fun findByEmail(employee: Employee): Employee? {
        TODO("Not yet implemented")
    }

    override fun findById(employee: Employee): Employee? {
        TODO("Not yet implemented")
    }
}