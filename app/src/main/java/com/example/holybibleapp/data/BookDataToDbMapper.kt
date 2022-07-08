package com.example.holybibleapp.data

import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.data.cache.BookDb
import com.example.holybibleapp.data.cache.DbWrapper

interface BookDataToDbMapper : Abstract.Mapper {

    fun mapToDb(id: Int, name: String, testament: String, db: DbWrapper): BookDb

    class Base : BookDataToDbMapper {

        override fun mapToDb(id: Int, name: String, testament: String, db: DbWrapper): BookDb {
            val bookDb = db.createObject(id)
            bookDb.name = name
            return bookDb
        }
    }
}