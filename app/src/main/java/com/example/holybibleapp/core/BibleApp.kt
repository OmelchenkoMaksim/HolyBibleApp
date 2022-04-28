package com.example.holybibleapp.core

import android.app.Application
import com.example.holybibleapp.data.net.BooksService
import retrofit2.Retrofit

class BibleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
                // todo log http calls
            .build()
        val service = retrofit.create(BooksService::class.java)
    }

    private companion object {

        const val BASE_URL = "https://bible-go-api.rkeplin.com/v1/"
    }
}