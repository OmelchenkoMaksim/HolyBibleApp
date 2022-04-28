package com.example.holybibleapp.data

import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.data.net.BookCloud
import com.example.holybibleapp.domain.BookDomain

sealed class BookData : Abstract.Object<BookDomain, BooksDataToDomainMapper>() {

    class Success(private val books: List<BookCloud>) : BookData() {

        override fun map(mapper: BooksDataToDomainMapper): BookDomain {
            return mapper.map(books)
        }
    }

    class Fail(private val e: Exception) : BookData() {

        override fun map(mapper: BooksDataToDomainMapper): BookDomain {
            return mapper.map(e)
        }
    }
}