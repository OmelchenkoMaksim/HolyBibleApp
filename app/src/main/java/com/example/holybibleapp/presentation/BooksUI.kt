package com.example.holybibleapp.presentation

import com.example.holybibleapp.R
import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.core.Abstract.Mapper.Empty
import com.example.holybibleapp.core.Book
import com.example.holybibleapp.domain.ErrorType
import com.example.holybibleapp.domain.ErrorType.NO_CONNECTION
import com.example.holybibleapp.domain.ErrorType.SERVICE_UNAVAILABLE

sealed class BooksUI : Abstract.Object<Unit, Empty>() {

    class Success(
        private val communication: BooksCommunication,
        private val books: List<Book>
    ) : BooksUI() {

        override fun map(mapper: Empty) {
            communication.show(books)
        }
    }

    class Fail(
        private val communication: BooksCommunication,
        private val errorType: ErrorType,
        private val resourceProvider: ResourceProvider
    ) :
        BooksUI() {

        override fun map(mapper: Empty) {
            val messageId = when (errorType) {
                NO_CONNECTION -> R.string.no_connection_message
                SERVICE_UNAVAILABLE -> R.string.service_unavailable_message
                else -> R.string.something_went_wrong
            }
            communication.show(resourceProvider.getString(messageId))
        }
    }
}
