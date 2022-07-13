package com.example.holybibleapp.presentation

import com.example.holybibleapp.core.Abstract.Mapper
import com.example.holybibleapp.core.Abstract.Object
import com.example.holybibleapp.core.Matcher
import com.example.holybibleapp.presentation.BookUi.StringMapper

// классы для отображения прогресса загрузки, успеха и неудачи
sealed class BookUi : Object<Unit, StringMapper>,
    Matcher<Int>,
    Collapsing,
    Comparing {

    override fun map(mapper: StringMapper) = Unit
    override fun matches(arg: Int) = false

    open fun changeState(): BookUi = Progress
    open fun saveId(cache: IdCache) = Unit

    // показываю на старте и при нажатии на попробовать снова
    object Progress : BookUi()

    abstract class Info(
        protected open val id: Int,
        protected open val name: String,
    ) : BookUi() {
        override fun map(mapper: StringMapper) = mapper.map(name)
        override fun matches(arg: Int) = arg == id
    }

    data class Base(override val id: Int, override val name: String) : Info(id, name) {
        override fun sameContent(bookUi: BookUi): Boolean =
            if (bookUi is Base) {
                name == bookUi.name
            } else false

        override fun same(bookUi: BookUi) = bookUi is Base && id == bookUi.id
    }

    // Стоит обратить внимание что поле объекта collapsed говорящее о том свернут объект или развернут val
    // это значит что при изменении переписываться будет весь элемент, а не только одно поле
    data class Testament(
        override val id: Int,
        override val name: String,
        private val collapsed: Boolean = false,
    ) : Info(id, name) {
        override fun collapseOrExpand(listener: BibleAdapter.CollapseListener) =
            listener.collapseOrExpand(id)

        override fun showCollapsed(mapper: CollapseMapper) = mapper.show(collapsed)
        override fun isCollapsed(): Boolean = collapsed
        override fun changeState() = Testament(id, name, !collapsed)
        override fun sameContent(bookUi: BookUi): Boolean =
            if (bookUi is Testament) {
                // очень важно проверять по всем полям
                name == bookUi.name && collapsed == bookUi.collapsed
            } else false

        override fun same(bookUi: BookUi) = bookUi is Testament && id == bookUi.id
        override fun saveId(cache: IdCache) = cache.save(id)
    }

    data class Fail(private val message: String) : BookUi() {
        override fun map(mapper: StringMapper) = mapper.map(message)
        override fun sameContent(bookUi: BookUi): Boolean =
            if (bookUi is Fail) {
                message == bookUi.message
            } else false

        override fun same(bookUi: BookUi) = sameContent(bookUi)
    }

    interface StringMapper : Mapper {
        fun map(text: String)
    }

    // Еще один интерфейс для полного следования принципам
    interface CollapseMapper : Mapper {
        fun show(collapse: Boolean)
    }
}

interface Collapsing {
    // collapseOrExpand метод специально написан что бы в адаптере его дергать
    fun collapseOrExpand(listener: BibleAdapter.CollapseListener) = Unit
    fun showCollapsed(mapper: BookUi.CollapseMapper) = Unit
    fun isCollapsed() = false
}

interface Comparing {
    // два метода ниже нужны для использования в дифутил
    fun sameContent(bookUi: BookUi) = false
    fun same(bookUi: BookUi) = false
}