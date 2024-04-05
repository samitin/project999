package com.geekbrains.progect999.core

import android.app.Application
import android.util.Log

class App : Application(),ProvideRepresentative ,ClearRepresentative{




    private lateinit var factory: ProvideRepresentative.Factory
    override fun onCreate() {
        super.onCreate()
        factory = ProvideRepresentative.Factory(ProvideRepresentative.MakeDependency(ProvideSharedPreferences.Core.Base(this),this))
    }
    override fun < T : Representative<*>> provideRepresentative(clazz: Class<T>): T = factory.provideRepresentative(clazz)
    override fun clear(clazz: Class<out Representative<*>>) = factory.clear(clazz)


    private fun log(msg : String){ Log.d(TAG,msg) }
    companion object{ const val TAG = "jsc91" }






}