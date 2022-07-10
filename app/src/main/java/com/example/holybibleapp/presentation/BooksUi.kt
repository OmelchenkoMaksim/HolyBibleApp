package com.example.holybibleapp.presentation

import com.example.holybibleapp.core.Abstract

sealed class BooksUi : Abstract.Object<Unit, BooksCommunication> {

    data class Base(private val books: List<BookUi>) : BooksUi() {

        // забота маппера мапить ошибки доменного слоя к юай слою
        override fun map(mapper: BooksCommunication) = mapper.map(books)
    }
}