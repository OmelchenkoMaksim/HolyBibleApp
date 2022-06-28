package com.example.holybibleapp.domain

import com.example.holybibleapp.core.Abstract.Object
import com.example.holybibleapp.presentation.BookUi

class BookDomain(
    private val id: Int,
    private val name: String
) : Object<BookUi, BookDomainToUiMapper> {

    override fun map(mapper: BookDomainToUiMapper) = mapper.map(id, name)
}