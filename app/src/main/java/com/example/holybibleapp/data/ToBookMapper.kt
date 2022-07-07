package com.example.holybibleapp.data

import com.example.holybibleapp.core.Abstract

/**
 * todo use one mapper
 * @see com.example.holybibleapp.data.cache.BooksCacheMapper
 * @author Me
 */

interface ToBookMapper : Abstract.Mapper {

    fun map(id: Int, name: String, testament: String): BookData

    class Base : ToBookMapper {

        override fun map(id: Int, name: String, testament: String) = BookData(id, name, testament)
    }
}