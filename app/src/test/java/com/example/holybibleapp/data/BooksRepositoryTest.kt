//package com.example.holybibleapp.data
//
//import com.example.holybibleapp.data.cache.BookDb
//import com.example.holybibleapp.data.cache.BooksCacheDataSource
//import com.example.holybibleapp.data.cache.BooksCacheMapper
//import com.example.holybibleapp.data.net.BookCloud
//import junit.framework.Assert.assertEquals
//import kotlinx.coroutines.runBlocking
//import org.junit.*
//import java.net.UnknownHostException
//
//class BooksRepositoryTest : BaseBooksRepositoryTest() {
//
//    val unknownHostException = UnknownHostException()
//
//    @Test
//    fun test_no_connection_no_cache() = runBlocking {
//        // нет интернета - нет кэша, создаем датасорс который не возвращает успех
//        val testCloudDataSource = TestBooksCloudDataSource(returnSuccess = false)
//        // и еще один датасорс возвращающий пустой список
//        val testCacheDataSource = TestBooksCacheDataSource(returnSuccess = false)
//        val repository = BooksRepository.Base(
//            testCloudDataSource,
//            testCacheDataSource,
//            BooksCloudMapper.Base(TestBookCloudMapper()), //todo
//            BooksCacheMapper.Base(TestBookCacheMapper())
//        )
//
//        val actual = repository.fetchBooks()
//        // и мы проверяем что наш результат эквивалентен фейлу
//        val expected = BooksData.Fail(unknownHostException)
//
//        assertEquals(expected, actual)
//    }
//
//    @Test
//    fun test_cloud_success_no_cache() = runBlocking {
//        // облако - работает
//        val testCloudDataSource = TestBooksCloudDataSource(returnSuccess = true)
//        // кэш - не
//        val testCacheDataSource = TestBooksCacheDataSource(returnSuccess = false)
//        val repository = BooksRepository.Base(
//            testCloudDataSource,
//            testCacheDataSource,
//            BooksCloudMapper.Base(TestBookCloudMapper()), //todo
//            BooksCacheMapper.Base(TestBookCacheMapper())
//        )
//
//        val actual = repository.fetchBooks()
//
//        val expected = BooksData.Success(
//            // тут леэит аналог того же что и в мнимом облаке
//            listOf(
//                Book(0, "name0"),
//                Book(1, "name1"),
//                Book(2, "name2"),
//            )
//        )
//        assertEquals(expected, actual)
//    }
//
//    @Test
//    fun test_no_connection_with_cache() = runBlocking {
//        // клауд кидает ошибку т.к. сети нет
//        val testCloudDataSource = TestBooksCloudDataSource(returnSuccess = false)
//        // но в кэше данные есть
//        val testCacheDataSource = TestBooksCacheDataSource(returnSuccess = true)
//        val repository = BooksRepository.Base(
//            testCloudDataSource,
//            testCacheDataSource,
//            BooksCloudMapper.Base(TestBookCloudMapper()), //todo
//            BooksCacheMapper.Base(TestBookCacheMapper())
//        )
//
//        val actual = repository.fetchBooks()
//        val expected = BooksData.Success(
//            // тут лежит аналог того же что и в мнимом кэше
//            listOf(
//                Book(10, "name10"),
//                Book(11, "name11"),
//                Book(12, "name12"),
//            )
//        )
//
//        assertEquals(expected, actual)
//    }
//
//    @Test
//    fun test_cloud_success_with_cache() = runBlocking {
//        // облако - работает
//        val testCloudDataSource = TestBooksCloudDataSource(returnSuccess = true)
//        // кэш - the same
//        val testCacheDataSource = TestBooksCacheDataSource(returnSuccess = true)
//        val repository = BooksRepository.Base(
//            testCloudDataSource,
//            testCacheDataSource,
//            BooksCloudMapper.Base(TestBookCloudMapper()), //todo
//            BooksCacheMapper.Base(TestBookCacheMapper())
//        )
//
//        val actual = repository.fetchBooks()
//        val expected = BooksData.Success(
//            // тут лежит аналог того же что и в мнимом кэше
//            listOf(
//                Book(10, "name10"),
//                Book(11, "name11"),
//                Book(12, "name12"),
//            )
//        )
//
//        assertEquals(expected, actual)
//    }
//
//    private inner class TestBooksCloudDataSource(
//        private val returnSuccess: Boolean
//    ) : BooksCloudDataSource {
//
//        override suspend fun fetchBooks(): List<BookCloud> {
//            if (returnSuccess) {
//                return listOf(
//                    BookCloud(0, "name0"),
//                    BookCloud(1, "name1"),
//                    BookCloud(2, "name2"),
//                )
//            } else {
//                throw unknownHostException
//            }
//        }
//    }
//
//    private inner class TestBooksCacheDataSource(
//        private val returnSuccess: Boolean
//    ) : BooksCacheDataSource {
//
//        override fun fetchBooks(): List<BookDb> {
//            return if (returnSuccess) {
//                listOf(
//                    BookDb().apply {
//                        id = 10
//                        name = "name10"
//                    },
//                    BookDb().apply {
//                        id = 11
//                        name = "name11"
//                    },
//                    BookDb().apply {
//                        id = 12
//                        name = "name12"
//                    },
//                )
//            } else {
//                emptyList()
//            }
//        }
//
//        override fun saveBooks(books: List<Book>) {
//            // not used here
//        }
//    }
//}