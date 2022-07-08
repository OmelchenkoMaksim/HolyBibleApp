package com.example.holybibleapp.data.cache

import com.example.holybibleapp.data.BookData
import com.example.holybibleapp.data.BookDataToDbMapper

interface BooksCacheDataSource {

    fun fetchBooks(): List<BookDb>

    fun saveBooks(books: List<BookData>)

    class Base(
        private val realmProvider: RealmProvider,
        private val mapper: BookDataToDbMapper
    ) : BooksCacheDataSource {

        override fun fetchBooks(): List<BookDb> {
            val realm = realmProvider.provide()
            // use can close all what it create inside without any exception
            realm.use { realmI ->
                val bookDb = realmI.where(BookDb::class.java).findAll() ?: emptyList()
                return realmI.copyFromRealm(bookDb)
            }
        }

        override fun saveBooks(books: List<BookData>) =
            realmProvider.provide().use { realm ->
                realm.executeTransaction {
                    val dbWrapper = DbWrapper.Base(it)
                    books.forEach { book ->
                        book.mapTo(mapper, dbWrapper)
                    }
                }
            }
    }
}