package com.geekbrains.progect999.core

import com.geekbrains.progect999.dashboard.DashboardModule
import com.geekbrains.progect999.dashboard.DashboardRepresentative
import com.geekbrains.progect999.main.MainModule
import com.geekbrains.progect999.main.MainRepresentative
import com.geekbrains.progect999.subscription.SubscriptionModule
import com.geekbrains.progect999.subscription.SubscriptionRepresentative

interface ProvideRepresentative {
    fun<T:Representative<*>>provideRepresentative(clazz: Class<T>):T

    class Factory(
        private val core:ProvideSharedPreferences.Core,
        private val clear:ClearRepresentative) : ProvideRepresentative {
        override fun <T : Representative<*>> provideRepresentative(clazz: Class<T>): T {
            return when (clazz) {
                MainRepresentative::class.java -> MainModule(core).representative()
                DashboardRepresentative::class.java -> DashboardModule(core).representative()
                SubscriptionRepresentative::class.java -> SubscriptionModule(core,clear).representative()
                else -> throw IllegalStateException("unknow class $clazz")
            } as T
        }
    }
}