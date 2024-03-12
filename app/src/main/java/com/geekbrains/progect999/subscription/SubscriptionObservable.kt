package com.geekbrains.progect999.subscription

import com.geekbrains.progect999.core.UiObservable

interface SubscriptionObservable : UiObservable<SubscriptionUiState>,SaveSubscriptionUiState{
    class Base : UiObservable.Single<SubscriptionUiState>(SubscriptionUiState.Empty),SubscriptionObservable {
        override fun saveState(saveState: SaveAndRestoreSubscriptionUiState.Save) {
            saveState.save(cache)
        }
    }
}