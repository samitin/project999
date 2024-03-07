package com.geekbrains.progect999.subscription

import com.geekbrains.progect999.core.ClearRepresentative
import com.geekbrains.progect999.core.ProvideSharedPreferences
import com.geekbrains.progect999.core.Representative
import com.geekbrains.progect999.dashboard.DashboardRepresentative
import com.geekbrains.progect999.main.Navigation
import com.geekbrains.progect999.main.Screen
import com.geekbrains.progect999.main.UserPremiumCache

interface SubscriptionRepresentative :Representative<Unit> {

    fun subscribe()

    class Base(
        private val clear: ClearRepresentative,
        private val userPremiumCache:UserPremiumCache.Save,
        private val navigation: Navigation.Update) : SubscriptionRepresentative {
        override fun subscribe() {
            userPremiumCache.saveUserPremium()//todo
            clear.clear(DashboardRepresentative::class.java)
            clear.clear(SubscriptionRepresentative::class.java)
            navigation.update(Screen.Dashboard)
        }


    }
}