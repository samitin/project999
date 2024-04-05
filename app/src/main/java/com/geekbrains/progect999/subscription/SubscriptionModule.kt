package com.geekbrains.progect999.subscription

import com.geekbrains.progect999.core.ClearRepresentative
import com.geekbrains.progect999.core.HandleDeath
import com.geekbrains.progect999.core.Module
import com.geekbrains.progect999.core.ProvideSharedPreferences
import com.geekbrains.progect999.main.UserPremiumCache
import com.geekbrains.progect999.subscription.domain.SubscriptionInteractor
import com.geekbrains.progect999.subscription.presentation.SubscriptionObservable
import com.geekbrains.progect999.subscription.presentation.SubscriptionRepresentative

class SubscriptionModule(
    private val core:ProvideSharedPreferences.Core,
    private val clear:ClearRepresentative
) : Module<SubscriptionRepresentative> {
    override fun representative() = SubscriptionRepresentative.Base(
        HandleDeath.Base(),
        SubscriptionObservable.Base(),
        clear,
        SubscriptionInteractor.Base(
            UserPremiumCache.Base(core.sharedPreferences())
        ),
        core.navigation()
    )
}