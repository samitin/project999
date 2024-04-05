package com.geekbrains.progect999.core

import com.geekbrains.progect999.dashboard.DashboardModule
import com.geekbrains.progect999.dashboard.DashboardRepresentative
import com.geekbrains.progect999.main.MainModule
import com.geekbrains.progect999.main.MainRepresentative
import com.geekbrains.progect999.subscription.SubscriptionModule
import com.geekbrains.progect999.subscription.presentation.SubscriptionRepresentative

interface ProvideRepresentative {
    fun<T:Representative<*>>provideRepresentative(clazz: Class<T>):T

    class MakeDependency(
        private val core : ProvideSharedPreferences.Core,
        private val clear: ClearRepresentative
    ) : ProvideRepresentative{
        override fun <T : Representative<*>> provideRepresentative(clazz: Class<T>): T {
            return when (clazz) {
                MainRepresentative::class.java -> MainModule(core).representative()
                DashboardRepresentative::class.java -> DashboardModule(core, clear).representative()
                SubscriptionRepresentative::class.java -> SubscriptionModule(core,clear).representative()
                else -> throw IllegalStateException("unknow class $clazz")
            } as T
        }

    }

    class Factory(
        private val makeDependency: ProvideRepresentative ) : ProvideRepresentative,ClearRepresentative {
        private val representativeMap = mutableMapOf<Class<out Representative<*>>,Representative<*>>()
        override fun <T : Representative<*>> provideRepresentative(clazz: Class<T>): T {
            if (representativeMap.containsKey(clazz))
                return representativeMap[clazz] as T
            else{
                val representative = makeDependency.provideRepresentative(clazz)
                representativeMap[clazz] = representative
                return representative
            }
        }

        override fun clear(clazz: Class<out Representative<*>>) {
            representativeMap.remove(clazz)
        }
    }
}