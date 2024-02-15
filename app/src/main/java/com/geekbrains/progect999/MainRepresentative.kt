package com.geekbrains.progect999

import android.util.Log
import androidx.annotation.MainThread

@MainThread
interface MainRepresentative {
    /**
     * начать получать обновления
     */
    fun startGettingUpdates(callBack: UiObserver<Int>)
    /**
     * прекратить получать обновления
     */
    fun stopGettingUpdates()
    /**
     * запустить асинхронный режим
     */
    fun startAsync()

    class Base(
        private val observable: UiObservable<Int>
    ):MainRepresentative{

        private val thread = Thread{
            Thread.sleep(5000)
            observable.update(R.string.app_name)
        }

        override fun startGettingUpdates(callBack: UiObserver<Int>) {
            observable.updateObserver(callBack)
        }
        override fun stopGettingUpdates() = observable.updateObserver()
        override fun startAsync() {
            if (!thread.isAlive)
                thread.start()
            Log.d("jsc91",thread.name)
        }



    }
}