package com.geekbrains.progect999.core

import androidx.annotation.MainThread

interface UiObservable<T:Any> : UiUpdate<T> ,UpdateObserver<T>{
    /**
     *обновить наблюдатель
     */
    fun clear()

    abstract class Single<T : Any>(
        private val empty: T
    ) : UiObservable<T> {
        @Volatile
        protected var cache : T = empty
        @Volatile
        private var observer: UiObserver<T> = UiObserver.Empty()
        override fun clear() {
            cache = empty
        }

        @MainThread
        override fun updateObserver(uiObserver: UiObserver<T>) = synchronized(Single::class.java) {
            observer = uiObserver
            if (!observer.isEmpty()){
                observer.update(cache)

            }
        }

        override fun update(data: T) = synchronized(Single::class.java) {
            cache = data
            if (!observer.isEmpty())
                 observer.update(cache)
        }

    }
}

interface UiUpdate<T : Any>{
    /**
     * обновить
     */
    fun update(data : T)
}

interface UpdateObserver<T:Any>{
    fun updateObserver(uiObserver: UiObserver<T> = UiObserver.Empty())
}


interface UiObserver<T:Any> : UiUpdate<T>,IsEmpty {

    class Empty<T : Any> : UiObserver<T> {
        /**
         * пусто?
         */
        override fun isEmpty() = true
        /**
         * обновить
         */
        override fun update(data: T) =Unit

    }

}