package com.marcoscouto.pontointeligente.pontointeligente.services

import com.marcoscouto.pontointeligente.pontointeligente.documents.Entry
import com.marcoscouto.pontointeligente.pontointeligente.enums.TypeEnum
import com.marcoscouto.pontointeligente.pontointeligente.repositories.EntryRepository
import com.marcoscouto.pontointeligente.pontointeligente.services.impl.EntryService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.time.LocalDate
import java.util.*

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EntryServiceTest {

    @MockBean
    private val repository: EntryRepository? = null

    @Autowired
    private val service: EntryService? = null

    private val ID: String = "1"
    private val EMPLOYEE_ID: String = "123"
    private val PAGE_REQUEST: PageRequest = PageRequest.of(0, 10)

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given(repository?.findById(ID)).willReturn(Optional.of(entry()))
        BDDMockito.given(repository?.findByEmployeeId(EMPLOYEE_ID, PAGE_REQUEST))
                .willReturn(PageImpl(ArrayList<Entry>()))
        BDDMockito.given(repository?.save(Mockito.any(Entry::class.java))).willReturn(entry())
    }

    @Test
    fun shouldFindById() = Assertions.assertNotNull(service?.findById(ID))

    @Test
    fun shouldFindByEmployeeId() = Assertions.assertNotNull(service?.findByEmployeeId(EMPLOYEE_ID, PAGE_REQUEST))

    @Test
    fun shouldSave() = Assertions.assertNotNull(service?.save(entry()))

    fun entry(): Entry = Entry(LocalDate.now(), TypeEnum.BEGIN_WORK,
            "123", null, null, "1")
}