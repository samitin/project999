package com.geekbrains.progect999.dashboard

import com.geekbrains.progect999.core.UiObservable

interface PremiumDashboardObservable :UiObservable<PremiumDashboardUiState> {
    class Base : UiObservable.Base<PremiumDashboardUiState>(PremiumDashboardUiState.Empty),PremiumDashboardObservable
}