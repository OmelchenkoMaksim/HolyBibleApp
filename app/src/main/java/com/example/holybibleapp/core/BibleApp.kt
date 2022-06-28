package com.example.holybibleapp.core

import android.app.Application
import com.example.holybibleapp.data.BookDataToDbMapper
import com.example.holybibleapp.data.BooksCloudDataSource
import com.example.holybibleapp.data.BooksCloudMapper
import com.example.holybibleapp.data.BooksRepository
import com.example.holybibleapp.data.ToBookMapper
import com.example.holybibleapp.data.cache.BooksCacheDataSource
import com.example.holybibleapp.data.cache.BooksCacheMapper
import com.example.holybibleapp.data.cache.RealmProvider
import com.example.holybibleapp.data.net.BooksService
import com.example.holybibleapp.domain.BaseBookDataToDomainMapper
import com.example.holybibleapp.domain.BaseBooksDataToDomainMapper
import com.example.holybibleapp.domain.BooksInteractor
import com.example.holybibleapp.presentation.BaseBookDomainToUiMapper
import com.example.holybibleapp.presentation.BaseBooksDomainToUiMapper
import com.example.holybibleapp.presentation.BooksCommunication
import com.example.holybibleapp.presentation.MainViewModel
import com.example.holybibleapp.presentation.ResourceProvider
import com.google.gson.Gson
import io.realm.Realm
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit.MINUTES

class BibleApp : Application() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.apply { loggingInterceptor.level = BODY }

        // делаем кастомный клиент с ИНТЕРЦЕПТОРОМ
        // connectTimeout - пытается подключиться в течении выбранного времени, и только после валится с ошибкой
        // readTimeout - пытается скачать данные в течении выбранного времени, и только после валится с ошибкой
        val client = OkHttpClient.Builder()
            .connectTimeout(1, MINUTES)
            .readTimeout(1, MINUTES)
            .addInterceptor(loggingInterceptor)
            .build()

        val gson = Gson()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .build()
        val service = retrofit.create(BooksService::class.java)

        val toBookMapper = ToBookMapper.Base()
        val cloudDataSource = BooksCloudDataSource.Base(service, gson)
        val realmProvider = RealmProvider.Base()
        val cacheDataSource = BooksCacheDataSource.Base(realmProvider = realmProvider, BookDataToDbMapper.Base())
        val booksCloudMapper = BooksCloudMapper.Base(toBookMapper)
        val booksCacheMapper = BooksCacheMapper.Base(toBookMapper)
        val booksRepository = BooksRepository.Base(
            cloudDataSource = cloudDataSource,
            cacheDataSource = cacheDataSource,
            booksCloudMapper = booksCloudMapper,
            booksCacheMapper = booksCacheMapper
        )
        val booksInteractor = BooksInteractor.Base(
            booksRepository = booksRepository,
            mapper = BaseBooksDataToDomainMapper(BaseBookDataToDomainMapper()),
        )
        val communication = BooksCommunication.Base()
        mainViewModel = MainViewModel(
            booksInteractor = booksInteractor,
            mapper = BaseBooksDomainToUiMapper(ResourceProvider.Base(this), BaseBookDomainToUiMapper()),
            communication = communication
        )
    }

    private companion object {

        const val BASE_URL = "https://bible-go-api.rkeplin.com/v1/"
    }
}