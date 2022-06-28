package com.example.holybibleapp.data

import android.util.Log
import com.example.holybibleapp.data.cache.BooksCacheDataSource
import com.example.holybibleapp.data.cache.BooksCacheMapper

interface BooksRepository {

    suspend fun fetchBooks(): BooksData

    class Base(
        private val cloudDataSource: BooksCloudDataSource,
        private val cacheDataSource: BooksCacheDataSource,
        private val booksCloudMapper: BooksCloudMapper,
        private val booksCacheMapper: BooksCacheMapper,
    ) : BooksRepository {

        override suspend fun fetchBooks() = try {
            val booksCacheList = cacheDataSource.fetchBooks()

            if (booksCacheList.isEmpty()) {
                val booksCloudList = cloudDataSource.fetchBooks()
                val books = booksCloudMapper.map(booksCloudList)
                cacheDataSource.saveBooks(books)
                BooksData.Success(books)
            } else {
                BooksData.Success(booksCacheMapper.map(booksCacheList))
            }
        } catch (e: Exception) {
            Log.e("error in ${this.javaClass.simpleName}", e.message.toString())
            BooksData.Fail(e)
        }
    }
}