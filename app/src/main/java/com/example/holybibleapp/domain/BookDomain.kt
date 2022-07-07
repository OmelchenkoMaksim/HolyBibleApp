package com.example.holybibleapp.domain

import com.example.holybibleapp.core.Abstract.Object
import com.example.holybibleapp.presentation.BookUi

sealed class BookDomain : Object<BookUi, BookDomainToUiMapper> {


    data class Base(
        private val id: Int,
        private val name: String
    ) : BookDomain() {

        override fun map(mapper: BookDomainToUiMapper) = mapper.map(id, name)
    }

  data  class Testament(private val type: TestamentType) : BookDomain() {
        override fun map(mapper: BookDomainToUiMapper): BookUi = mapper.map(type.getId(), type.name)
    }
}

enum class TestamentType(private val id: Int) {
    OLD(Int.MIN_VALUE),
    NEW(Int.MAX_VALUE);

    fun getId() = id
}