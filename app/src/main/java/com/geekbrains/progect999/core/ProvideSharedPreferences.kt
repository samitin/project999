package com.geekbrains.progect999.core

import android.content.Context
import android.content.SharedPreferences
import com.geekbrains.progect999.main.Navigation

interface ProvideSharedPreferences {

    fun sharedPreferences() :SharedPreferences
    interface ProvideNavigation{
        fun navigation(): Navigation.Mutable
    }
    interface Core : ProvideNavigation, ProvideSharedPreferences{
        class Base(private val context: Context):Core {
            private val navigation = Navigation.Base
            override fun navigation(): Navigation.Mutable = navigation

            override fun sharedPreferences(): SharedPreferences {
                return context.getSharedPreferences("project999",Context.MODE_PRIVATE)
            }
        }
    }

}