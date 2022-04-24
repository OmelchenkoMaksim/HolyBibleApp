package com.example.holybibleapp.data

import com.example.holybibleapp.core.Abstract
import com.example.holybibleapp.core.Abstract.Mapper.Empty
import com.example.holybibleapp.domain.BookDomain

sealed class BookData : Abstract.Object<BookDomain, Empty>()
