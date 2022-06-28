package com.example.holybibleapp.data.cache

import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.data.BookData
import com.example.holybibleapp.data.ToBookMapper
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

// it needs for realm
open class BookDb : RealmObject(), Abstract.Object<BookData, ToBookMapper> {

    @PrimaryKey
    var id = -1
    var name = ""
    override fun map(mapper: ToBookMapper) = BookData(id, name)
}