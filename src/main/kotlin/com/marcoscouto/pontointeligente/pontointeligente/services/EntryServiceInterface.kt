package com.marcoscouto.pontointeligente.pontointeligente.services

import com.marcoscouto.pontointeligente.pontointeligente.documents.Entry
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface EntryServiceInterface {

    fun findByEmployeeId(employeeId: String, pageRequest: PageRequest): Page<Entry>

    fun findById(id: String): Entry?

    fun save(entry: Entry): Entry

    fun delete(id: String)
}