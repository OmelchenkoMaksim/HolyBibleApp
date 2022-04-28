package com.example.holybibleapp.domain

import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.core.Abstract.Mapper.Empty
import com.example.holybibleapp.presentation.BookUI

sealed class BookDomain : Abstract.Object<BookUI, Empty>() {

    class Success() : BookDomain() {

        override fun map(mapper: Empty): BookUI {
            TODO("Not yet implemented")
        }
    }

    class Fail(errorType: Int) : BookDomain() {

        override fun map(mapper: Empty): BookUI {
            TODO("Not yet implemented")
        }
    }
}