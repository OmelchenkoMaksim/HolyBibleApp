package com.example.holybibleapp.presentation

import com.example.holybibleapp.R
import com.example.holybibleapp.domain.BookDomainToUiMapper
import com.example.holybibleapp.domain.ErrorType
import org.junit.Assert.assertEquals
import org.junit.Test

class BaseBooksDomainToUiMapperTest {

    @Test
    fun test_fail() {
        val resourceProvider = TestResourceProvider()
        val mapper = BaseBooksDomainToUiMapper(
            resourceProvider = resourceProvider,
            bookMapper = object : BookDomainToUiMapper {
                override fun map(id: Int, name: String): BookUi {
                    throw IllegalStateException("not used here")
                }
            })
        var actual = mapper.map(ErrorType.NO_CONNECTION)
        var expected = BooksUi.Base(listOf(BookUi.Fail("noConnection")))
        assertEquals(expected, actual)
        actual = mapper.map(ErrorType.SERVICE_UNAVAILABLE)
        expected = BooksUi.Base(listOf(BookUi.Fail("serviceUnavailable")))
        assertEquals(expected, actual)
        actual = mapper.map(ErrorType.GENERIC_ERROR)
        expected = BooksUi.Base(listOf(BookUi.Fail("somethingWentWrong")))
        assertEquals(expected, actual)
    }

    class TestResourceProvider : ResourceProvider {
        override fun getString(id: Int): String =
            when (id) {
                R.string.no_connection_message -> "noConnection"
                R.string.service_unavailable_message -> "serviceUnavailable"
                else -> "somethingWentWrong"
            }
    }
}