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

    data class Testament(private val type: TestamentType) : BookDomain() {
        override fun map(mapper: BookDomainToUiMapper): BookUi = type.map(mapper)
    }
}