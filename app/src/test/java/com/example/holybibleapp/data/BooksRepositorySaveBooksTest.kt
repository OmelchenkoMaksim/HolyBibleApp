package com.example.holybibleapp.data

import com.example.holybibleapp.data.cache.BookDb
import com.example.holybibleapp.data.cache.BooksCacheDataSource
import com.example.holybibleapp.data.cache.BooksCacheMapper
import com.example.holybibleapp.data.cache.DbWrapper
import com.example.holybibleapp.data.net.BookCloud
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

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
            BooksCloudMapper.Base(TestToBookMapper()),
            BooksCacheMapper.Base(TestToBookMapper())
        )

        // тут он сначала проверяет кэш - там пусто
        // он идет в облако берет оттуда и кладет в кэш
        val actualCloud = repository.fetchBooks()
        val expectedCloud = BooksData.Success(
            listOf(
                BookData(0, "name0", "ot"),
                BookData(1, "name1", "nt")
            )
        )

        assertEquals(expectedCloud, actualCloud)

        // тут он снова идет в кэш и в этот раз там есть
        // он берет из кэша и потом сравнивает
        val actualCache = repository.fetchBooks()
        val expectedCache = BooksData.Success(
            listOf(
                BookData(0, "name0 db", "ot db"),
                BookData(1, "name1 db", "nt db")
            )
        )

        assertEquals(expectedCache, actualCache)
    }

    private inner class TestBooksCloudDataSource : BooksCloudDataSource {

        override suspend fun fetchBooks(): List<BookCloud> {
            return listOf(
                BookCloud(0, "name0", "ot"),
                BookCloud(1, "name1", "nt")
            )
        }
    }

    private inner class TestBooksCacheDataSource : BooksCacheDataSource {

        private val list = ArrayList<BookDb>()

        override fun fetchBooks() = list

        override fun saveBooks(books: List<BookData>) {
            books.map { book ->
                list.add(book.mapTo(object : BookDataToDbMapper {
                    override fun mapToDb(
                        id: Int, name: String,
                        testament: String, db: DbWrapper,
                    ) = BookDb().apply {
                        this.id = id
                        this.name = "$name db"
                        this.testament = "$testament db"
                    }
                }, object : DbWrapper {
                    override fun createObject(id: Int) = BookDb().apply {
                        this.id = id
                    }
                }))
            }
        }
    }
}