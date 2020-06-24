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
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
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

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: String): ResponseEntity<Response<EntryDTO>> {
        val response: Response<EntryDTO> = Response()
        val entry: Entry? = entryService.findById(id)

        if (entry == null) {
            response.errors.add("Lançamento não encontrado para o id $id")
            return ResponseEntity.badRequest().body(response)
        }

        response.data = convertEntryDTO(entry)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/employee/{employeeId}")
    fun findByEmployeeId(@PathVariable("employeeId") employeeId: String,
                         @RequestParam(value = "pag", defaultValue = "0") pag: Int,
                         @RequestParam(value = "ord", defaultValue = "id") ord: String,
                         @RequestParam(value = "dir", defaultValue = "DESC") dir: String)
            : ResponseEntity<Response<Page<EntryDTO>>> {

        val response: Response<Page<EntryDTO>> = Response()
        val pageRequest: PageRequest = PageRequest.of(pag, qntPerPage, Sort.Direction.valueOf(dir), ord)
        val entries: Page<Entry> = entryService.findByEmployeeId(employeeId, pageRequest)
        val entriesDTO: Page<EntryDTO> = entries.map { entry -> convertEntryDTO(entry) }

        response.data = entriesDTO
        return ResponseEntity.ok(response)
    }

    @PutMapping
    fun update(@PathVariable("id") id: String, @Valid @RequestBody entryDTO: EntryDTO,
               result: BindingResult): ResponseEntity<Response<EntryDTO>> {

        val response: Response<EntryDTO> = Response()
        validateEmployee(entryDTO, result)
        entryDTO.id = id
        val entry: Entry = convertDTOforEntry(entryDTO, result)

        if (result.hasErrors()) {
            for (erro in result.allErrors)
                erro.defaultMessage?.let { response.errors.add(it) }
            return ResponseEntity.badRequest().body(response)
        }

        entryService.save(entry)
        response.data = convertEntryDTO(entry)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String): ResponseEntity<Response<String>>{
        val response: Response<String> = Response()
        val entry: Entry? = entryService.findById(id)

        if(entry == null){
            response.errors.add("Erro ao remover lançamento. Registro não encotrado para o id $id")
            return ResponseEntity.badRequest().body(response)
        }

        entryService.delete(id)
        return ResponseEntity.ok(Response<String>())
    }

    private fun validateEmployee(entryDTO: EntryDTO, result: BindingResult) {
        if (entryDTO.employeeId == null) {
            result.addError(ObjectError("Funcionário",
                    "Funcionário não informado"))
            return
        }

        val employee: Employee? = employeeService.findById(entryDTO.employeeId)
        if(employee == null)
            result.addError(ObjectError("Funcionário", "Funcionário não encontrado. Id inexistente."))
    }

    private fun convertEntryDTO(entry: Entry): EntryDTO? =
            EntryDTO(dateFormat.format(entry.date), entry.type.toString(),
                    entry.description, entry.localization, entry.employeeId, entry.id)

    private fun convertDTOforEntry(entryDTO: EntryDTO, result: BindingResult): Entry {
        if (entryDTO.id != null) {
            val entry: Entry? = entryService.findById(entryDTO.id!!)
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