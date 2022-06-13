package com.example.holybibleapp.core

import android.app.Application
import com.example.holybibleapp.data.BooksCloudDataSource
import com.example.holybibleapp.data.BooksCloudMapper
import com.example.holybibleapp.data.BooksRepository
import com.example.holybibleapp.data.cache.BookCacheMapper
import com.example.holybibleapp.data.cache.BooksCacheDataSource
import com.example.holybibleapp.data.cache.BooksCacheMapper
import com.example.holybibleapp.data.cache.RealmProvider
import com.example.holybibleapp.data.net.BookCloudMapper
import com.example.holybibleapp.data.net.BooksService
import retrofit2.Retrofit
import com.example.holybibleapp.data.BooksRepository
import com.example.holybibleapp.domain.BaseBooksDataToDomainMapper
import com.example.holybibleapp.domain.BooksInteractor

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