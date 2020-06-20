package com.marcoscouto.pontointeligente.pontointeligente.services.impl

import com.marcoscouto.pontointeligente.pontointeligente.documents.Entry
import com.marcoscouto.pontointeligente.pontointeligente.repositories.EntryRepository
import com.marcoscouto.pontointeligente.pontointeligente.services.EntryServiceInterface
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class EntryService(val repository: EntryRepository) : EntryServiceInterface {

    override fun findByEmployeeId(employeeId: String, pageRequest: PageRequest): Page<Entry> =
            repository.findByEmployeeId(employeeId, pageRequest)

    override fun findById(id: String): Entry? =
            repository.findById(id).get()

    override fun save(entry: Entry): Entry? =
            repository.save(entry)

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }
}