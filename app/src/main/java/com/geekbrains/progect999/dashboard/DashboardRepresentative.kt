package com.geekbrains.progect999.dashboard

import com.geekbrains.progect999.core.ClearRepresentative
import com.geekbrains.progect999.core.Representative
import com.geekbrains.progect999.core.UiObserver
import com.geekbrains.progect999.main.Navigation
import com.geekbrains.progect999.main.Screen

interface DashboardRepresentative :Representative<PremiumDashboardUiState> {
    fun observed() = Unit
    fun play()
    class Base(
        private val clear : ClearRepresentative,
        private val navigation: Navigation.Update) : DashboardRepresentative{
        override fun play() {
            clear.clear(DashboardRepresentative::class.java)
            navigation.update(Screen.Subscription)
        }

    }

    class Premium(private val observable:PremiumDashboardObservable) : DashboardRepresentative {
        override fun observed() = observable.clear()
        override fun play() = observable.update(PremiumDashboardUiState.Playing)
        override fun startGettingUpdates(callBack: UiObserver<PremiumDashboardUiState>) = observable.updateObserver(callBack)
        override fun stopGettingUpdates() = observable.updateObserver()

    }
}