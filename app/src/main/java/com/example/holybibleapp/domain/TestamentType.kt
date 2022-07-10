package com.example.holybibleapp.domain

import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.core.Matcher
import com.example.holybibleapp.presentation.BookUi

enum class TestamentType(private val id: Int) : Abstract.Object<BookUi, BookDomainToUiMapper>,
    Matcher<Int> {
    OLD(Int.MIN_VALUE),
    NEW(Int.MAX_VALUE);

    override fun matches(arg: Int) = this.id == arg
    override fun map(mapper: BookDomainToUiMapper) = mapper.map(id, name)
}