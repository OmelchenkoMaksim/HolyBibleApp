package com.example.holybibleapp.presentation

import com.example.holybibleapp.R
import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.domain.BookDomain
import com.example.holybibleapp.domain.BookDomainToUiMapper
import com.example.holybibleapp.domain.ErrorType
import com.example.holybibleapp.domain.ErrorType.NO_CONNECTION
import com.example.holybibleapp.domain.ErrorType.SERVICE_UNAVAILABLE

sealed class BooksUi : Abstract.Object<Unit, BooksCommunication> {

    data class Success(
        private val books: List<BookDomain>,
        private val bookMapper: BookDomainToUiMapper
    ) : BooksUi() {

        override fun map(mapper: BooksCommunication) {
            val booksUi = books.map {
                it.map(bookMapper)
            }
            mapper.map(booksUi)
        }
    }

    data class Fail(
        private val errorType: ErrorType,
        private val resourceProvider: ResourceProvider
    ) : BooksUi() {

        // забота маппера мапить ошибки доменного слоя к юай слою
        override fun map(mapper: BooksCommunication) {
            val messageId = when (errorType) {
                NO_CONNECTION -> R.string.no_connection_message
                SERVICE_UNAVAILABLE -> R.string.service_unavailable_message
                else -> R.string.something_went_wrong
            }
            val message = resourceProvider.getString(messageId)
            mapper.map(listOf(BookUi.Fail(message)))
        }
    }
}
