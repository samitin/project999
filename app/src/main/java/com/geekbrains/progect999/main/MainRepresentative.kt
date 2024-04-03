package com.geekbrains.progect999.main

import android.util.Log
import androidx.annotation.MainThread
import com.geekbrains.progect999.core.Representative
import com.geekbrains.progect999.core.UiObservable
import com.geekbrains.progect999.core.UiObserver

@MainThread
interface MainRepresentative :Representative<Screen>{


    fun observed()
    /**
     * запустить асинхронный режим
     */
    fun showDashboard(firstTime:Boolean)

    class Base(
        private val navigation: Navigation.Mutable
    ): MainRepresentative {

        private val thread =  Thread{
            Thread.sleep(5000)
            Log.d("jsc91",Thread.currentThread().name)
        }
        override fun startGettingUpdates(callBack: UiObserver<Screen>) {
            navigation.updateObserver(callBack)
        }
        override fun stopGettingUpdates() = navigation.updateObserver()
        override fun observed() = navigation.clear()

        override fun showDashboard(firstTime: Boolean) {
            if (firstTime)
                navigation.update(Screen.Dashboard)
        }
    }
}
