package com.example.holybibleapp.data

import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.data.net.BookCloud
import com.example.holybibleapp.domain.BookDomain
import retrofit2.HttpException
import java.net.UnknownHostException

interface BooksDataToDomainMapper : Abstract.Mapper {

    fun map(books: List<BookCloud>): BookDomain
    fun map(e: Exception): BookDomain

    class Base : BooksDataToDomainMapper {

        override fun map(books: List<BookCloud>): BookDomain {

            // TODO:
            return BookDomain.Success()
        }

        override fun map(e: Exception): BookDomain {
            val errorType = when (e) {
                is UnknownHostException -> 0 // enum class ErrorType MoConnection
                is HttpException -> 1 // ServiceUnavailable
                else -> 2 // GenericException
            }
            return BookDomain.Fail(errorType)
        }
    }
}