package com.geekbrains.progect999.core

import com.geekbrains.progect999.main.Screen

interface Representative<T : Any> {
    /**
     * начать получать обновления
     */
    fun startGettingUpdates(callBack: UiObserver<T>) = Unit
    /**
     * прекратить получать обновления
     */
    fun stopGettingUpdates() = Unit
}