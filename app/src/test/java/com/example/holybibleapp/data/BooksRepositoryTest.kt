package com.example.holybibleapp.data

import com.example.holybibleapp.data.cache.BookDb
import com.example.holybibleapp.data.cache.BooksCacheDataSource
import com.example.holybibleapp.data.cache.BooksCacheMapper
import com.example.holybibleapp.data.net.BookCloud
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.net.UnknownHostException

class BooksRepositoryTest : BaseBooksRepositoryTest() {

    val unknownHostException = UnknownHostException()

    @Test
    fun test_no_connection_no_cache() = runBlocking {
        // нет интернета - нет кэша, создаем датасорс который не возвращает успех
        val testCloudDataSource = TestBooksCloudDataSource(returnSuccess = false)
        // и еще один датасорс возвращающий пустой список
        val testCacheDataSource = TestBooksCacheDataSource(returnSuccess = false)
        val repository = BooksRepository.Base(
            testCloudDataSource,
            testCacheDataSource,
            BooksCloudMapper.Base(TestToBookMapper()),
            BooksCacheMapper.Base(TestToBookMapper())
        )

        val actual = repository.fetchBooks()
        // и мы проверяем что наш результат эквивалентен фейлу
        val expected = BooksData.Fail(unknownHostException)

        assertEquals(expected, actual)
    }

    @Test
    fun test_cloud_success_no_cache() = runBlocking {
        // облако - работает
        val testCloudDataSource = TestBooksCloudDataSource(returnSuccess = true)
        // кэш - не
        val testCacheDataSource = TestBooksCacheDataSource(returnSuccess = false)
        val repository = BooksRepository.Base(
            testCloudDataSource,
            testCacheDataSource,
            BooksCloudMapper.Base(TestToBookMapper()),
            BooksCacheMapper.Base(TestToBookMapper())
        )

        val actual = repository.fetchBooks()

        val expected = BooksData.Success(
            // тут леэит аналог того же что и в мнимом облаке
            listOf(
                BookData(0, "name0", "ot"),
                BookData(1, "name1", "ot"),
                BookData(2, "name2", "ot"),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun test_no_connection_with_cache() = runBlocking {
        // клауд кидает ошибку т.к. сети нет
        val testCloudDataSource = TestBooksCloudDataSource(returnSuccess = false)
        // но в кэше данные есть
        val testCacheDataSource = TestBooksCacheDataSource(returnSuccess = true)
        val repository = BooksRepository.Base(
            testCloudDataSource,
            testCacheDataSource,
            BooksCloudMapper.Base(TestToBookMapper()),
            BooksCacheMapper.Base(TestToBookMapper())
        )

        val actual = repository.fetchBooks()
        val expected = BooksData.Success(
            // тут лежит аналог того же что и в мнимом кэше
            listOf(
                BookData(10, "name10", "nt"),
                BookData(11, "name11", "nt"),
                BookData(12, "name12", "nt"),
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun test_cloud_success_with_cache() = runBlocking {
        // облако - работает
        val testCloudDataSource = TestBooksCloudDataSource(returnSuccess = true)
        // кэш - the same
        val testCacheDataSource = TestBooksCacheDataSource(returnSuccess = true)
        val repository = BooksRepository.Base(
            testCloudDataSource,
            testCacheDataSource,
            BooksCloudMapper.Base(TestToBookMapper()),
            BooksCacheMapper.Base(TestToBookMapper())
        )

        val actual = repository.fetchBooks()
        val expected = BooksData.Success(
            // тут лежит аналог того же что и в мнимом кэше
            listOf(
                BookData(10, "name10", "nt"),
                BookData(11, "name11", "nt"),
                BookData(12, "name12", "nt"),
            )
        )

        assertEquals(expected, actual)
    }

    private inner class TestBooksCloudDataSource(
        private val returnSuccess: Boolean
    ) : BooksCloudDataSource {

        override suspend fun fetchBooks(): List<BookCloud> {
            if (returnSuccess) {
                return listOf(
                    BookCloud(0, "name0", "ot"),
                    BookCloud(1, "name1", "ot"),
                    BookCloud(2, "name2", "ot"),
                )
            } else {
                throw unknownHostException
            }
        }
    }

    private inner class TestBooksCacheDataSource(
        private val returnSuccess: Boolean
    ) : BooksCacheDataSource {

        override fun fetchBooks(): List<BookDb> {
            return if (returnSuccess) {
                listOf(
                    BookDb().apply {
                        id = 10
                        name = "name10"
                        testament = "nt"
                    },
                    BookDb().apply {
                        id = 11
                        name = "name11"
                        testament = "nt"
                    },
                    BookDb().apply {
                        id = 12
                        name = "name12"
                        testament = "nt"
                    },
                )
            } else {
                emptyList()
            }
        }

        override fun saveBooks(books: List<BookData>) {
            // not used here
        }
    }
}