package com.example.holybibleapp.presentation

import com.example.holybibleapp.core.Book
import com.example.holybibleapp.domain.BooksDomainToUiMapper
import com.example.holybibleapp.domain.ErrorType

class BaseBooksDomainToUiMapper(private val communication: BooksCommunication,
private val resourceProvider: ResourceProvider) :
    BooksDomainToUiMapper {

    override fun map(books: List<Book>): BooksUI = BooksUI.Success(communication, books)

    override fun map(errorType: ErrorType): BooksUI = BooksUI.Fail(communication, errorType, resourceProvider)
}