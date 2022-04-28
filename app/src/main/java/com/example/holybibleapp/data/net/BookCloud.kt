package com.example.holybibleapp.data.net

import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.data.BookData
import com.google.gson.annotations.SerializedName

data class BookCloud(
    @SerializedName("id")
    private val id: Int,
    @SerializedName("name")
    private val name: String
) : Abstract.Object<BookData, BookCloudToDomainMapper>() {

    override fun map(mapper: BookCloudToDomainMapper): BookData {
        return mapper.map(id, name)
    }
}

// [{"id":1,"name":"Genesis","testament":"OT","genre":{"id":1,"name":"Law"}}