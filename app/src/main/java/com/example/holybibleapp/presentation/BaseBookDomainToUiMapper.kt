package com.example.holybibleapp.presentation

import com.example.holybibleapp.R
import com.example.holybibleapp.domain.BookDomainToUiMapper
import com.example.holybibleapp.domain.TestamentType

class BaseBookDomainToUiMapper(private val resourceProvider: ResourceProvider) :
    BookDomainToUiMapper {

    override fun map(id: Int, name: String) = when {
        TestamentType.NEW.matches(id) -> BookUi.Testament(
            id = id,
            name = resourceProvider.getString(R.string.new_testament)
        )
        TestamentType.OLD.matches(id) -> BookUi.Testament(
            id = id,
            name = resourceProvider.getString(R.string.old_testament)
        )
        else -> BookUi.Base(id, name)
    }
}