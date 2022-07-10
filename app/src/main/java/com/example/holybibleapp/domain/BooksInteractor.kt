package com.example.holybibleapp.domain

import com.example.holybibleapp.data.BooksDataToDomainMapper
import com.example.holybibleapp.data.BooksRepository

interface BooksInteractor {

    suspend fun fetchBooks(): BooksDomain

    class Base(
        private val booksRepository: BooksRepository,
        private val mapper: BooksDataToDomainMapper,
    ) : BooksInteractor {

        override suspend fun fetchBooks() = booksRepository.fetchBooks().map(mapper = mapper)
    }
}