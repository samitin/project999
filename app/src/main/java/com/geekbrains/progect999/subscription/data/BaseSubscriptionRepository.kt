package com.geekbrains.progect999.subscription.data

import com.geekbrains.progect999.main.UserPremiumCache
import com.geekbrains.progect999.subscription.domain.SubscriptionRepository
import kotlinx.coroutines.delay

class BaseSubscriptionRepository(
    private val foregroundServiceWrapper: ForegroundServiceWrapper,
    private val cloudDataSource: SubscriptionCloudDataSource,
    private val userPremiumCash:UserPremiumCache.Mutable,
) : SubscriptionRepository {
    override fun isPremiumUser(): Boolean = userPremiumCash.isUserPremium()

    override fun subscribe() {
        foregroundServiceWrapper.start()

    }

    override suspend fun subscribeInternal() {
        cloudDataSource.subscribe()
        userPremiumCash.saveUserPremium()
    }
}