package com.geekbrains.progect999.subscription

import android.os.Bundle
import com.geekbrains.progect999.core.SaveAndRestoreState

interface SaveAndRestoreSubscriptionUiState {

    interface Save : SaveAndRestoreState.Save<SubscriptionUiState>

    interface Restore : SaveAndRestoreState.Restore<SubscriptionUiState>
    interface Mutable : Save,Restore
    class Base(bundle: Bundle?) : SaveAndRestoreState.Abstract<SubscriptionUiState>(
        bundle, KEY,SubscriptionUiState::class.java //todo check restore Tiramisu
    ),Mutable{

    }
}
private const val KEY = "SaveAndRestoreSubscriptionUiState"