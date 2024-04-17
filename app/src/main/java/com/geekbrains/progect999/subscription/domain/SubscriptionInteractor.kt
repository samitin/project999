package com.geekbrains.progect999.subscription.domain

import com.geekbrains.progect999.main.UserPremiumCache
import com.geekbrains.progect999.subscription.presentation.SubscriptionUiState
import kotlinx.coroutines.delay

interface SubscriptionInteractor {
    suspend fun subscribe()
    class Base(private val repository: SubscriptionRepository) : SubscriptionInteractor {
        override suspend fun subscribe() {
                repository.subscribe()
        }
    }
}