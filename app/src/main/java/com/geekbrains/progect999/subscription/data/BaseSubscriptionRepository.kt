package com.geekbrains.progect999.subscription.data

import com.geekbrains.progect999.main.UserPremiumCache
import com.geekbrains.progect999.subscription.domain.SubscriptionRepository
import kotlinx.coroutines.delay

class BaseSubscriptionRepository(
    private val cloudDataSource: SubscriptionCloudDataSource,
    private val userPremiumCash:UserPremiumCache.Save,
) : SubscriptionRepository {
    override suspend fun subscribe() {
        cloudDataSource.subscribe()
        userPremiumCash.saveUserPremium()
    }
}