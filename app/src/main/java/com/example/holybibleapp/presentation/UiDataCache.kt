package com.example.holybibleapp.presentation

interface UiDataCache {

    fun cache(list: List<BookUi>): BooksUi
    fun changeState(id: Int): List<BookUi>
    fun saveState()

    class Base(private val cache: IdCache) : UiDataCache {

        private val cachedList = ArrayList<BookUi>()

        override fun cache(list: List<BookUi>): BooksUi {
            cachedList.clear()
            cachedList.addAll(list)
            var newList: List<BookUi> = ArrayList(list)
            val ids = cache.read()
            ids.forEach { id ->
                newList = changeState(id)
            }
            return BooksUi.Base(newList)
        }

        override fun changeState(id: Int): List<BookUi> {
            val newList = ArrayList<BookUi>()
            val item = cachedList.find {
                it.matches(id)
            }

            var add = false
            cachedList.forEachIndexed { index, book ->
                if (book is BookUi.Testament) {
                    if (item == book) {
                        val element = book.changeState()
                        cachedList[index] = element
                        newList.add(element)
                        add = !element.isCollapsed()
                    } else {
                        newList.add(book)
                        add = !book.isCollapsed()
                    }
                } else {
                    if (add) newList.add(book)
                }
            }

            return newList
        }

        // сохранение схлопнутых состояний что бы после перезапуска оно восстановилось
        override fun saveState() {
            cache.start()
            cachedList.filter {
                it.isCollapsed()
            }.forEach {
                it.saveId(cache)
            }
            cache.finish()
        }
    }
}

