package com.marcoscouto.pontointeligente.pontointeligente.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.marcoscouto.pontointeligente.pontointeligente.documents.Employee
import com.marcoscouto.pontointeligente.pontointeligente.documents.Entry
import com.marcoscouto.pontointeligente.pontointeligente.dtos.EntryDTO
import com.marcoscouto.pontointeligente.pontointeligente.enums.ProfileEnum
import com.marcoscouto.pontointeligente.pontointeligente.enums.TypeEnum
import com.marcoscouto.pontointeligente.pontointeligente.services.impl.EmployeeService
import com.marcoscouto.pontointeligente.pontointeligente.services.impl.EntryService
import com.marcoscouto.pontointeligente.pontointeligente.utils.PasswordUtils
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.print.attribute.standard.Media

@SpringBootTest
@AutoConfigureMockMvc
class EntryControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val entryService: EntryService? = null

    @MockBean
    private val employeeService: EmployeeService? = null

    private val URL_BASE: String = "/api/entries/"
    private val ID_EMPLOYEE = "1"
    private val ID_ENTRY = "1"
    private val TYPE = TypeEnum.BEGIN_WORK.name
    private val DATE = LocalDateTime.now()

    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

//    @Test
//    @Throws(Exception::class)
//    @WithMockUser
//    fun testRegisterEntry() {
//        BDDMockito.given<Entry>(entryService?.save(getEntryData())).willReturn(getEntryData())
//        BDDMockito.given<Employee>(employeeService?.findById(ID_EMPLOYEE)).willReturn(employee())
//
//        mvc!!.perform(MockMvcRequestBuilders
//                .post(URL_BASE)
//                .content(getJsonRequestPost())
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk)
//                .andExpect(jsonPath("$.data.type").value(TYPE))
//                .andExpect(jsonPath("$.data.date").value(dateFormat.format(DATE)))
//                .andExpect(jsonPath("$.data.employeeId").value(ID_EMPLOYEE))
//                .andExpect(jsonPath("$.errors").isEmpty)
//
//    }

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun testRegisterEntryEmployeeInvalidId() {
        BDDMockito.given<Employee>(employeeService?.findById(ID_EMPLOYEE)).willReturn(null)

        mvc!!.perform(MockMvcRequestBuilders
                .post(URL_BASE)
                .content(getJsonRequestPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.errors").value("Funcionário não encontrado, ID inexistente"))
                .andExpect(jsonPath("$.data").isEmpty)
    }


    @Test
    @Throws(Exception::class)
    fun testRemoveEntry() {
        BDDMockito.given<Entry>(entryService?.findById(ID_EMPLOYEE)).willReturn(getEntryData())

        mvc!!.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_ENTRY)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Throws(JsonProcessingException::class)
    private fun getJsonRequestPost(): String {
        val entryDTO = EntryDTO(dateFormat.format(DATE), TYPE, "Description", "1.243,4.345", ID_EMPLOYEE)
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(entryDTO)
    }


    private fun getEntryData(): Entry =
            Entry(DATE, TypeEnum.valueOf(TYPE), ID_EMPLOYEE, "Description",
                    "1.243,4.345", ID_ENTRY)

    private fun getEntryDTO(): EntryDTO =
            EntryDTO(DATE.toString(), TypeEnum.valueOf(TYPE).toString(), "Description",
                    "1.243,4.345", ID_EMPLOYEE, null)

    private fun employee() =
            Employee("Name", "email@email", PasswordUtils().generateBCrypt("123456"),
                    "000.000.000-00", ProfileEnum.ROLE_USER, ID_EMPLOYEE)


}