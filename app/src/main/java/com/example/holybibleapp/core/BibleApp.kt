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
import com.example.holybibleapp.domain.BaseBooksDataToDomainMapper
import com.example.holybibleapp.domain.BooksInteractor
import com.example.holybibleapp.presentation.BaseBooksDomainToUiMapper
import com.example.holybibleapp.presentation.BooksCommunication
import com.example.holybibleapp.presentation.MainViewModel
import com.example.holybibleapp.presentation.ResourceProvider
import io.realm.Realm
import retrofit2.Retrofit

class BibleApp : Application() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
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
            cloudDataSource = booksCacheMapper,
            cacheDataSource = booksCloudMapper,
            booksCloudMapper = cloudDataSource,
            booksCacheMapper = cacheDataSource
        )
        val booksInterctor = BooksInteractor.Base(booksRepository, BaseBooksDataToDomainMapper())
        val communication = BooksCommunication.Base()
        mainViewModel = MainViewModel(
            booksInteractor = booksInterctor,
            mapper = BaseBooksDomainToUiMapper(communication, ResourceProvider.Base(this)),
            communication = communication
        )
    }

    private companion object {

        const val BASE_URL = "https://bible-go-api.rkeplin.com/v1/"
    }
}