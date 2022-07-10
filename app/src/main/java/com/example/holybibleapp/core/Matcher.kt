package com.example.holybibleapp.core

interface Matcher<T> {
    fun matches(arg: T): Boolean
}