package com.example.holybibleapp.data.net

import okhttp3.ResponseBody
import retrofit2.http.GET

interface BooksService {

    @GET("books")
    suspend fun fetchBooks(): ResponseBody
}

// https://bible-go-api.rkeplin.com/v1/