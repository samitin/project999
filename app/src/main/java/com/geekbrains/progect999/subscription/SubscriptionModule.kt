package com.geekbrains.progect999.subscription

import com.geekbrains.progect999.core.ClearRepresentative
import com.geekbrains.progect999.core.DispatchersList
import com.geekbrains.progect999.core.HandleDeath
import com.geekbrains.progect999.core.Module
import com.geekbrains.progect999.core.ProvideSharedPreferences
import com.geekbrains.progect999.core.RunAsync
import com.geekbrains.progect999.main.UserPremiumCache
import com.geekbrains.progect999.subscription.data.BaseSubscriptionRepository
import com.geekbrains.progect999.subscription.data.ForegroundServiceWrapper
import com.geekbrains.progect999.subscription.data.SubscriptionCloudDataSource
import com.geekbrains.progect999.subscription.domain.SubscriptionInteractor
import com.geekbrains.progect999.subscription.presentation.SubscriptionObservable
import com.geekbrains.progect999.subscription.presentation.SubscriptionRepresentative
import com.geekbrains.progect999.subscription.presentation.SubscriptionUiMapper

class SubscriptionModule(
    private val core:ProvideSharedPreferences.Core,
    private val clear:ClearRepresentative
) : Module<SubscriptionRepresentative> {
    override fun representative() : SubscriptionRepresentative.Base {
        val observable = SubscriptionObservable.Base()
        return SubscriptionRepresentative.Base(
            core.runAsync(),
            HandleDeath.Base(),
            observable,
            clear,
            SubscriptionInteractor.Base(
                BaseSubscriptionRepository(
                    ForegroundServiceWrapper.Base(core.workManager()),
                    SubscriptionCloudDataSource.Base(),
                    UserPremiumCache.Base(core.sharedPreferences())
            )
        ),
            core.navigation(),
            SubscriptionUiMapper(observable)
        )
    }
}