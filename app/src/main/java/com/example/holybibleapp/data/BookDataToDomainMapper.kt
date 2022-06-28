package com.example.holybibleapp.data

import com.example.holybibleapp.core.Abstract.Mapper
import com.example.holybibleapp.domain.BookDomain

interface BookDataToDomainMapper : Mapper {

    fun map(id: Int, name: String): BookDomain
}