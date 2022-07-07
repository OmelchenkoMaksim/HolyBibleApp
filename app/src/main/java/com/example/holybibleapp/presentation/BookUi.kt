package com.example.holybibleapp.presentation

import com.example.holybibleapp.core.Abstract.Mapper
import com.example.holybibleapp.core.Abstract.Object
import com.example.holybibleapp.presentation.BookUi.StringMapper

// классы для отображения прогресса загрузки, успеха и неудачи
sealed class BookUi : Object<Unit, StringMapper> {

    override fun map(mapper: StringMapper) = Unit

    // показываю на старте и при нажатии на попробовать снова
    object Progress : BookUi()

    abstract class Info(
        private val id: Int,
        private val name: String
    ) : BookUi() {
        override fun map(mapper: StringMapper) = mapper.map(name)
    }

    class Base(id: Int, name: String) : Info(id, name)

    class Testament(id: Int, name: String) : Info(id, name)

    class Fail(private val message: String) : BookUi() {

        override fun map(mapper: StringMapper) = mapper.map(message)
    }

    interface StringMapper : Mapper {

        fun map(text: String)
    }
}
