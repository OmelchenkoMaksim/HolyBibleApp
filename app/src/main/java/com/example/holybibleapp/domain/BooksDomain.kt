package com.example.holybibleapp.domain

import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.data.BookData
import com.example.holybibleapp.data.BookDataToDomainMapper
import com.example.holybibleapp.domain.ErrorType.GENERIC_ERROR
import com.example.holybibleapp.domain.ErrorType.NO_CONNECTION
import com.example.holybibleapp.domain.ErrorType.SERVICE_UNAVAILABLE
import com.example.holybibleapp.presentation.BooksUi
import retrofit2.HttpException
import java.net.UnknownHostException

sealed class BooksDomain : Abstract.Object<BooksUi, BooksDomainToUiMapper> {

    class Success(
        private val books: List<BookData>,
        private val bookMapper: BookDataToDomainMapper
    ) : BooksDomain() {

        override fun map(mapper: BooksDomainToUiMapper) = mapper.map(books.map { it.map(bookMapper) })
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
