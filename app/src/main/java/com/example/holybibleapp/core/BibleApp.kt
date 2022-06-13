package com.example.holybibleapp.core

import android.app.Application

class BibleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            // todo log http calls
            .build()
        val service = retrofit.create(BooksService::class.java)

        val cloudDataSource = BooksCloudMapper.Base(BookCloudMapper.Base())
        val cacheDataSource = BooksCacheMapper.Base(BookCacheMapper.Base())
        val booksCloudMapper: BooksCacheDataSource = BooksCacheDataSource.Base(RealmProvider.Base())
        val booksCacheMapper: BooksCloudDataSource = BooksCloudDataSource.Base(service)
        val booksRepository = BooksRepository.Base(
            booksCacheMapper,
            booksCloudMapper,
            cloudDataSource,
            cacheDataSource
        )
        val booksInterctor = BooksInteractor.Base(booksRepository, BaseBooksDataToDomainMapper())
    }

    private companion object {

        const val BASE_URL = "https://bible-go-api.rkeplin.com/v1/"
    }
}