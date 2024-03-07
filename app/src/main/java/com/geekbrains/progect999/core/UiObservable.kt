package com.geekbrains.progect999.core

import androidx.annotation.MainThread

interface UiObservable<T:Any> : UiUpdate<T> ,UpdateObserver<T>{
    /**
     *обновить наблюдатель
     */


    open class Single<T : Any> : UiObservable<T> {
        @Volatile
        private var cache : T? = null
        @Volatile
        private var observer: UiObserver<T> = UiObserver.Empty()


        @MainThread
        override fun updateObserver(uiObserver: UiObserver<T>) = synchronized(Single::class.java) {
            observer = uiObserver
            if (!observer.isEmpty()){
                cache?.let {
                    observer.update(it)
                    cache = null
                }
            }
        }

        override fun update(data: T) = synchronized(Single::class.java) {
            if (observer.isEmpty()){
                cache = data
            }else{
                cache = null
                observer.update(data)
            }
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


interface UiObserver<T:Any> : UiUpdate<T> {
    fun isEmpty():Boolean = false
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