package com.example.holybibleapp.core

abstract class Abstract {

    interface Object<T, M : Mapper> {

        fun map(mapper: M): T
    }

    interface Mapper {
        // смарт-контракт Empty предоставлен т.к. он помогает в случае
        // если UI объект не мапится ни к одному слою (он финальный, он терминальный)
        // короче используется когда не к чему мапить
        class Empty : Mapper
    }
}