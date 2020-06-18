package com.marcoscouto.pontointeligente.pontointeligente.services

import com.marcoscouto.pontointeligente.pontointeligente.documents.Company
import com.marcoscouto.pontointeligente.pontointeligente.repositories.CompanyRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
@AutoConfigureDataMongo
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CompanyServiceTest {

    @Autowired
    val service: CompanyServiceInterface? = null

    @MockBean
    private val repository: CompanyRepository? = null
    private val CNPJ = "000.000.000/0000-00"

    @BeforeEach
    @Throws(Exception::class)
    internal fun setUp() {
        BDDMockito.given(repository?.save(company())).willReturn(company())
        BDDMockito.given(repository?.findByCnpj(CNPJ)).willReturn(company())
    }

    @Test
    fun shouldFindByCnpj() = Assertions.assertNotNull(service?.findByCnpj(CNPJ))

    @Test
    fun shouldSaveCompany() = Assertions.assertNotNull(service?.save(company()))

    private fun company(): Company = Company("Company", CNPJ, "1")

}