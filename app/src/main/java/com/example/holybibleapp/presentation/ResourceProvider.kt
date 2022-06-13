package com.example.holybibleapp.presentation

import android.content.Context
import androidx.annotation.StringRes

// провайдер используется для тестов т.к. пркидывать контекст очень громоздко
interface ResourceProvider {

    fun getString(@StringRes id: Int) : String

    class Base(private val context:Context):ResourceProvider{

        override fun getString(id: Int) = context.getString(id)
    }
}