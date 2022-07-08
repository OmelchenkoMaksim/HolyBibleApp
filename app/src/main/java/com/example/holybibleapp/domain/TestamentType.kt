package com.example.holybibleapp.domain

import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.presentation.BookUi

enum class TestamentType(private val id: Int) : Abstract.Object<BookUi, BookDomainToUiMapper> {
    OLD(Int.MIN_VALUE),
    NEW(Int.MAX_VALUE);

    fun matches(id: Int) = this.id == id
    override fun map(mapper: BookDomainToUiMapper) = mapper.map(id, name)
}