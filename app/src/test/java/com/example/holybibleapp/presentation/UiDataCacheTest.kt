package com.example.holybibleapp.presentation

import org.junit.Assert.assertEquals
import org.junit.Test

class UiDataCacheTest {

    // mock list что бы тестить на нем
    private val source = listOf(
        BookUi.Testament(-1, "old"),
        BookUi.Base(1, "first"),
        BookUi.Base(2, "second"),
        BookUi.Testament(0, "new"),
        BookUi.Base(3, "third"),
        BookUi.Base(4, "forth"),
    )

    @Test
    fun test_collapse_first() {
        val dataCache = UiDataCache.Base(IdCache.Empty())
        dataCache.cache(source)

        val actual = dataCache.changeState(-1)
        val expected = listOf(
            BookUi.Testament(-1, "old", true),
            BookUi.Testament(0, "new"),
            BookUi.Base(3, "third"),
            BookUi.Base(4, "forth"),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun test_collapse_second() {
        val dataCache = UiDataCache.Base(IdCache.Empty())
        dataCache.cache(source)

        val actual = dataCache.changeState(0)
        val expected = listOf(
            BookUi.Testament(-1, "old"),
            BookUi.Base(1, "first"),
            BookUi.Base(2, "second"),
            BookUi.Testament(0, "new", true),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun test_collapse_first_then_expand() {
        val dataCache = UiDataCache.Base(IdCache.Empty())
        dataCache.cache(source)

        var actual = dataCache.changeState(-1)
        var expected = listOf(
            BookUi.Testament(-1, "old", true),
            BookUi.Testament(0, "new"),
            BookUi.Base(3, "third"),
            BookUi.Base(4, "forth"),
        )
        assertEquals(expected, actual)

        actual = dataCache.changeState(-1)
        expected = listOf(
            BookUi.Testament(-1, "old"),
            BookUi.Base(1, "first"),
            BookUi.Base(2, "second"),
            BookUi.Testament(0, "new"),
            BookUi.Base(3, "third"),
            BookUi.Base(4, "forth"),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun test_collapse_second_then_expand() {
        val dataCache = UiDataCache.Base(IdCache.Empty())
        dataCache.cache(source)

        var actual = dataCache.changeState(0)
        var expected = listOf(
            BookUi.Testament(-1, "old"),
            BookUi.Base(1, "first"),
            BookUi.Base(2, "second"),
            BookUi.Testament(0, "new", true),
        )
        assertEquals(expected, actual)

        actual = dataCache.changeState(0)
        expected = listOf(
            BookUi.Testament(-1, "old"),
            BookUi.Base(1, "first"),
            BookUi.Base(2, "second"),
            BookUi.Testament(0, "new"),
            BookUi.Base(3, "third"),
            BookUi.Base(4, "forth"),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun test_collapse_first_collapse_second() {
        val dataCache = UiDataCache.Base(IdCache.Empty())
        dataCache.cache(source)

        var actual = dataCache.changeState(-1)
        var expected = listOf(
            BookUi.Testament(-1, "old", true),
            BookUi.Testament(0, "new"),
            BookUi.Base(3, "third"),
            BookUi.Base(4, "forth"),
        )
        assertEquals(expected, actual)

        actual = dataCache.changeState(0)
        expected = listOf(
            BookUi.Testament(-1, "old", true),
            BookUi.Testament(0, "new", true),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun test_collapse_first_collapse_second_expand_first() {
        val dataCache = UiDataCache.Base(IdCache.Empty())
        dataCache.cache(source)

        var actual = dataCache.changeState(-1)
        var expected = listOf(
            BookUi.Testament(-1, "old", true),
            BookUi.Testament(0, "new"),
            BookUi.Base(3, "third"),
            BookUi.Base(4, "forth"),
        )
        assertEquals(expected, actual)

        actual = dataCache.changeState(0)
        expected = listOf(
            BookUi.Testament(-1, "old", true),
            BookUi.Testament(0, "new", true),
        )
        assertEquals(expected, actual)

        actual = dataCache.changeState(-1)
        expected = listOf(
            BookUi.Testament(-1, "old"),
            BookUi.Base(1, "first"),
            BookUi.Base(2, "second"),
            BookUi.Testament(0, "new", true),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun test_collapse_second_collapse_first_expand_second() {
        val dataCache = UiDataCache.Base(IdCache.Empty())
        dataCache.cache(source)

        var actual = dataCache.changeState(0)
        var expected = listOf(
            BookUi.Testament(-1, "old"),
            BookUi.Base(1, "first"),
            BookUi.Base(2, "second"),
            BookUi.Testament(0, "new", true),
        )
        assertEquals(expected, actual)

        actual = dataCache.changeState(-1)
        expected = listOf(
            BookUi.Testament(-1, "old", true),
            BookUi.Testament(0, "new", true),
        )
        assertEquals(expected, actual)

        actual = dataCache.changeState(0)
        expected = listOf(
            BookUi.Testament(-1, "old", true),
            BookUi.Testament(0, "new"),
            BookUi.Base(3, "third"),
            BookUi.Base(4, "forth"),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun test_collapse_first_collapse_second_expand_first_expand_second() {
        val dataCache = UiDataCache.Base(IdCache.Empty())
        dataCache.cache(source)

        var actual = dataCache.changeState(-1)
        var expected = listOf(
            BookUi.Testament(-1, "old", true),
            BookUi.Testament(0, "new"),
            BookUi.Base(3, "third"),
            BookUi.Base(4, "forth"),
        )
        assertEquals(expected, actual)

        actual = dataCache.changeState(0)
        expected = listOf(
            BookUi.Testament(-1, "old", true),
            BookUi.Testament(0, "new", true),
        )
        assertEquals(expected, actual)

        actual = dataCache.changeState(-1)
        expected = listOf(
            BookUi.Testament(-1, "old"),
            BookUi.Base(1, "first"),
            BookUi.Base(2, "second"),
            BookUi.Testament(0, "new", true),
        )
        assertEquals(expected, actual)

        actual = dataCache.changeState(0)
        expected = listOf(
            BookUi.Testament(-1, "old"),
            BookUi.Base(1, "first"),
            BookUi.Base(2, "second"),
            BookUi.Testament(0, "new"),
            BookUi.Base(3, "third"),
            BookUi.Base(4, "forth"),
        )
        assertEquals(expected, actual)
    }
}