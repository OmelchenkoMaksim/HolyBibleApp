package com.example.holybibleapp.data

import com.example.holybibleapp.core.Book
import com.example.holybibleapp.data.cache.BookDb
import com.example.holybibleapp.data.cache.BooksCacheDataSource
import com.example.holybibleapp.data.cache.BooksCacheMapper
import com.example.holybibleapp.data.net.BookCloud
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.*

// юнит тесты должны проверять функциональность!
class BooksRepositorySaveBooksTest : BaseBooksRepositoryTest() {

    // этот тест проверяет логику сохранения и получения из кэша
    @Test
    fun test_save_books() = runBlocking {

        val testCloudDataSource = TestBooksCloudDataSource()
        val testCacheDataSource = TestBooksCacheDataSource()
        val repository = BooksRepository.Base(
            testCloudDataSource,
            testCacheDataSource,
            BooksCloudMapper.Base(TestBookCloudMapper()), //todo
            BooksCacheMapper.Base(TestBookCacheMapper())
        )

        // тут он сначала проверяет кэш - там пусто
        // он идет в облако берет оттуда и кладет в кэш
        val actualCloud = repository.fetchBooks()
        val expectedCloud = BooksData.Success(
            listOf(
                Book(0, "name0"),
                Book(1, "name1")
            )
        )

        assertEquals(expectedCloud, actualCloud)

        // тут он снова идет в кэш и в этот раз там есть
        // он берет из кэша и потом сравнивает
        val actualCache = repository.fetchBooks()
        val expectedCache = BooksData.Success(
            listOf(
                Book(0, "name0 db"),
                Book(1, "name1 db")
            )
        )

        assertEquals(expectedCache, actualCache)
    }

    private inner class TestBooksCloudDataSource : BooksCloudDataSource {

        override suspend fun fetchBooks(): List<BookCloud> {
            return listOf(
                BookCloud(0, "name0"),
                BookCloud(1, "name1"),
            )
        }
    }

    private inner class TestBooksCacheDataSource() : BooksCacheDataSource {

        private val list = ArrayList<BookDb>()

        override fun fetchBooks() = list

        override fun saveBooks(books: List<Book>) {
            books.map { book ->
                list.add(BookDb().apply {
                    this.id = book.id
                    this.name = "${book.name} db"
                })
            }
        }
    }
}