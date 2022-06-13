package com.example.holybibleapp.domain

import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.core.Book
import com.example.holybibleapp.domain.ErrorType.GENERIC_ERROR
import com.example.holybibleapp.domain.ErrorType.SERVICE_UNAVAILABLE
import com.example.holybibleapp.presentation.BooksUI
import retrofit2.HttpException
import java.net.UnknownHostException

sealed class BookDomain : Abstract.Object<BooksUI, BooksDomainToUiMapper>() {

    class Success(private val books: List<Book>) : BookDomain() {

        override fun map(mapper: BooksDomainToUiMapper): BooksUI = mapper.map(books)
    }

    class Fail(private val e: Exception) : BookDomain() {

        override fun map(mapper: BooksDomainToUiMapper) = mapper.map(
            when (e) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is HttpException -> SERVICE_UNAVAILABLE
                else -> GENERIC_ERROR
            }
        )
    }
}
interface BooksDataToDomainMapper : Abstract.Mapper {
    fun map(books: List<Book> ): BookDomain
    fun map(e: Exception): BookDomain
}