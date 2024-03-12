package com.geekbrains.progect999.core

import android.os.Build
import android.os.Bundle
import java.io.Serializable

interface SaveAndRestoreState {
    interface Save<T : Serializable> {
        fun save(data:T)
    }
    interface Restore <T : Serializable> : IsEmpty {
        fun restore() : T
    }
    interface  Mutble<T:Serializable> : Save<T> , Restore<T>
    abstract class Abstract <T : Serializable>(
        private val bundle: Bundle?,
        private val key: String,
        private val clazz : Class<T>
    ) : SaveAndRestoreState.Mutble<T> {
        override fun save(data: T) {
            bundle!!.putSerializable(key,data)
        }

        override fun restore(): T {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                bundle!!.getSerializable(key,clazz)!!
            else
                bundle!!.getSerializable(key)!! as T
        }

        override fun isEmpty(): Boolean = bundle == null

    }
}