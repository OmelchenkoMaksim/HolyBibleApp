//package com.example.holybibleapp.data
//
//import com.example.holybibleapp.data.cache.BookDb
//
//abstract class BaseBooksRepositoryTest {
//
//    protected inner class TestBookCacheMapper : BookCacheMapper {
//
//        override fun map(bookDb: BookDb) = BookData(bookDb.id, bookDb.name)
//    }
//
//    protected class TestBookCloudMapper : ToBookMapper {
//
//        override fun map(id: Int, name: String) = BookData(id, name)
//    }
//}