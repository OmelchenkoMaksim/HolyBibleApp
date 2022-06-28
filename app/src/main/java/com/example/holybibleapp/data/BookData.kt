package com.example.holybibleapp.data

import com.example.holybibleapp.core.Abstract.Mapper
import com.example.holybibleapp.core.Abstract.Object
import com.example.holybibleapp.data.cache.BookDb
import com.example.holybibleapp.domain.BookDomain
import io.realm.Realm

class BookData(
    private val id: Int,
    private val name: String
) : ToBookDb<BookDb, BookDataToDbMapper>,
    Object<BookDomain, BookDataToDomainMapper> {

    override fun map(mapper: BookDataToDomainMapper) = mapper.map(id, name)
    override fun mapTo(mapper: BookDataToDbMapper, realm: Realm) = mapper.mapToDb(id, name, realm)
}

interface ToBookDb<T, M : Mapper> {

    fun mapTo(mapper: M, realm: Realm): T
}