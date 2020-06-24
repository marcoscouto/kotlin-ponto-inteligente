package com.marcoscouto.pontointeligente.pontointeligente.controllers

import com.marcoscouto.pontointeligente.pontointeligente.documents.Employee
import com.marcoscouto.pontointeligente.pontointeligente.documents.Entry
import com.marcoscouto.pontointeligente.pontointeligente.dtos.EntryDTO
import com.marcoscouto.pontointeligente.pontointeligente.enums.ProfileEnum
import com.marcoscouto.pontointeligente.pontointeligente.enums.TypeEnum
import com.marcoscouto.pontointeligente.pontointeligente.response.Response
import com.marcoscouto.pontointeligente.pontointeligente.services.impl.EmployeeService
import com.marcoscouto.pontointeligente.pontointeligente.services.impl.EntryService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.Valid

@RestController
@RequestMapping("/api/entries")
class EntryController(val entryService: EntryService,
                      val employeeService: EmployeeService) {

    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    @Value("\${pagination.qnt_per_page}")
    val qntPerPage: Int = 15

    @PostMapping
    fun save(@Valid @RequestBody entryDTO: EntryDTO,
             result: BindingResult): ResponseEntity<Response<EntryDTO>> {

        val response: Response<EntryDTO> = Response()
        checkEmployee(entryDTO, result)

        if (result.hasErrors()) {
            for (erro in result.allErrors)
                response.errors.add(erro.defaultMessage!!)
            return ResponseEntity.badRequest().body(response)
        }

        var entry = convertDTOforEntry(entryDTO, result)
        entry = entryService.save(entry)!!
        response.data = convertEntryDTO(entry)

        return ResponseEntity.ok().body(response);
    }

    private fun convertEntryDTO(entry: Entry): EntryDTO? =
            EntryDTO(dateFormat.format(entry.date), entry.type.toString(),
                    entry.description, entry.localization, entry.employeeId, entry.id)

    private fun convertDTOforEntry(entryDTO: EntryDTO, result: BindingResult): Entry {
        if (entryDTO.id != null) {
            val entry: Entry? = entryService.findById(entryDTO.id)
            if (entry != null)
                result.addError(ObjectError("Lançamento", "Lançamento não encontrado"))
        }
        return Entry(LocalDateTime.parse(entryDTO.date, dateFormat), TypeEnum.valueOf(entryDTO.type!!),
                entryDTO.employeeId!!, entryDTO.description,
                entryDTO.localization, entryDTO.id)
    }

    private fun checkEmployee(entryDTO: EntryDTO, result: BindingResult) {
        if (entryDTO.employeeId == null) {
            result.addError(ObjectError("Funcionário", "Funcionário não informado"))
            return
        }

        val employee: Employee? = employeeService.findById(entryDTO.employeeId)
        if (employee == null)
            result.addError(ObjectError("Funcionário", "Funcionário não encontrado, ID inexistente"))
    }


}