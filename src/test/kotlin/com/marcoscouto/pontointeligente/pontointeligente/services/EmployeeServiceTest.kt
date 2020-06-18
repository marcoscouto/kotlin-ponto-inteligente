package com.marcoscouto.pontointeligente.pontointeligente.services

import com.marcoscouto.pontointeligente.pontointeligente.documents.Employee
import com.marcoscouto.pontointeligente.pontointeligente.enums.ProfileEnum
import com.marcoscouto.pontointeligente.pontointeligente.repositories.EmployeeRepository
import com.marcoscouto.pontointeligente.pontointeligente.utils.PasswordUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest
@AutoConfigureDataMongo
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeServiceTest() {

    @Autowired
    val service: EmployeeServiceInterface? = null

    @MockBean
    private val repository: EmployeeRepository? = null
    private val EMAIL: String = "test@teste.com"
    private val CPF: String = "00.000.000-00"
    private val ID: String = "123abc"

    @BeforeAll
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given(repository?.save(Mockito.any(Employee::class.java))).willReturn(employee())
        BDDMockito.given(repository?.findByCpf(CPF)).willReturn(employee())
        BDDMockito.given(repository?.findByEmail(EMAIL)).willReturn(employee())
        BDDMockito.given(repository?.findById(ID)).willReturn(Optional.of(employee()))
    }

    @Test
    fun shouldSave() = Assertions.assertNotNull(service?.save(employee()))

    @Test
    fun shouldFindByCpf() = Assertions.assertNotNull(service?.findByCpf(employee()))

    @Test
    fun shouldFindByEmail() = Assertions.assertNotNull(service?.findByEmail(employee()))

    @Test
    fun shouldFindById() = Assertions.assertNotNull(service?.findById(employee()))

    fun employee() = Employee("Employee", EMAIL, PasswordUtils().generateBCrypt("123"),
            CPF, ProfileEnum.ROLE_USER, "1", 1.0,
            1f, 1f, ID)


}