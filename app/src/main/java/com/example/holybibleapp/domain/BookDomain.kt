package com.example.holybibleapp.domain

import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.core.Abstract.Mapper.Empty
import com.example.holybibleapp.presentation.BookUI

sealed class BookDomain : Abstract.Object<BookUI, Empty>() {
}