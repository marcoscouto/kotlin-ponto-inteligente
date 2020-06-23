package com.marcoscouto.pontointeligente.pontointeligente.config

import com.marcoscouto.pontointeligente.pontointeligente.documents.Company
import com.marcoscouto.pontointeligente.pontointeligente.documents.Employee
import com.marcoscouto.pontointeligente.pontointeligente.enums.ProfileEnum
import com.marcoscouto.pontointeligente.pontointeligente.repositories.CompanyRepository
import com.marcoscouto.pontointeligente.pontointeligente.repositories.EmployeeRepository
import com.marcoscouto.pontointeligente.pontointeligente.utils.PasswordUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration

@Configuration
class TestConfig @Autowired constructor(val companyRepository: CompanyRepository,
                 val employeeRepository: EmployeeRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();

        var company = Company("Empresa 1", "123456", null)
        company = companyRepository.save(company)

        var admin = Employee("Admin", "admin@empresa.com",
                PasswordUtils().generateBCrypt("123456"), "000.000.000-00",
                ProfileEnum.ROLE_ADMIN, company.id!!)
        admin = employeeRepository.save(admin)

        var employee = Employee("Funcionário", "funcionario@empresa.com",
                PasswordUtils().generateBCrypt("123456"), "000.000.000-00",
                ProfileEnum.ROLE_USER, company.id!!)
        employee = employeeRepository.save(employee)

        println("Empresa ID: " + company.id)
        println("Admin ID: " + admin.id)
        println("Funcionário ID: " + employee.id)
    }

}