package com.example.holybibleapp.domain

import com.example.holybibleapp.core.Abstract.Mapper
import com.example.holybibleapp.presentation.BookUi

interface BookDomainToUiMapper : Mapper {

    fun map(id: Int, name: String): BookUi
}