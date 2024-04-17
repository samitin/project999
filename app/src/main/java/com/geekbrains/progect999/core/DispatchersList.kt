package com.geekbrains.progect999.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatchersList {
    fun background(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher
    class Base():DispatchersList {
        override fun background() = Dispatchers.IO

        override fun ui() = Dispatchers.Main
    }
}