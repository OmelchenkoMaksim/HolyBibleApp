package com.example.holybibleapp.data.net

import okhttp3.ResponseBody
import retrofit2.http.GET

// https://bible-go-api.rkeplin.com/v1/

interface BooksService {

    @GET("books")
    suspend fun fetchBooks(): ResponseBody
}
