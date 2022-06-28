package com.example.holybibleapp.data

import com.example.holybibleapp.core.Abstract

interface BooksCloudMapper : Abstract.Mapper {

    fun map(cloudList: List<Abstract.Object<BookData, ToBookMapper>>): List<BookData>

    class Base(private val bookMapper: ToBookMapper) : BooksCloudMapper {

        override fun map(cloudList: List<Abstract.Object<BookData, ToBookMapper>>): List<BookData> =
            cloudList.map { bookCloud ->
                bookCloud.map(bookMapper)
            }
    }
}