package com.marcoscouto.pontointeligente.pontointeligente.repositories

import com.marcoscouto.pontointeligente.pontointeligente.documents.Entry
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface EntryRepository : MongoRepository<Entry, String> {

    fun findByEmployeeId(employeeId : String, pageable: Pageable) : Page<Entry>

}