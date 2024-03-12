package com.geekbrains.progect999.core

import android.app.Application
import android.util.Log
import com.geekbrains.progect999.dashboard.DashboardModule
import com.geekbrains.progect999.dashboard.DashboardRepresentative
import com.geekbrains.progect999.main.MainModule
import com.geekbrains.progect999.main.MainRepresentative
import com.geekbrains.progect999.subscription.SubscriptionModule
import com.geekbrains.progect999.subscription.SubscriptionRepresentative

class App : Application(),ProvideRepresentative ,ClearRepresentative{



    private val representativeMap = mutableMapOf<Class<out Representative<*>>,Representative<*>>()
    private lateinit var core: ProvideSharedPreferences.Core
    private lateinit var factory: ProvideRepresentative.Factory
    override fun onCreate() {
        super.onCreate()
        core = ProvideSharedPreferences.Core.Base(this)
        factory = ProvideRepresentative.Factory(core,this)
    }

    private fun log(msg : String){
        Log.d(TAG,msg)
    }
    companion object{
        const val TAG = "jsc91"
    }

    override fun < T : Representative<*>> provideRepresentative(clazz: Class<T>): T =
        if (representativeMap.containsKey(clazz)){
            representativeMap[clazz] as T
        }else{
            factory.provideRepresentative(clazz).let {
                representativeMap[clazz] = it
                it
            }
        }

    override fun clear(clazz: Class<out Representative<*>>) {
        representativeMap.remove(clazz)
    }






}