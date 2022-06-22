package com.example.holybibleapp.domain

import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.core.Book
import com.example.holybibleapp.domain.ErrorType.GENERIC_ERROR
import com.example.holybibleapp.domain.ErrorType.NO_CONNECTION
import com.example.holybibleapp.domain.ErrorType.SERVICE_UNAVAILABLE
import com.example.holybibleapp.presentation.BooksUI
import retrofit2.HttpException
import java.net.UnknownHostException

sealed class BooksDomain : Abstract.Object<BooksUI, BooksDomainToUiMapper>() {

    class Success(private val books: List<Book>) : BooksDomain() {

        override fun map(mapper: BooksDomainToUiMapper) = mapper.map(books)
    }

    class Fail(private val e: Exception) : BooksDomain() {

        override fun map(mapper: BooksDomainToUiMapper) =
            mapper.map(
                when (e) {
                    is UnknownHostException -> NO_CONNECTION
                    is HttpException -> SERVICE_UNAVAILABLE
                    else -> GENERIC_ERROR
                }
            )
    }
}
