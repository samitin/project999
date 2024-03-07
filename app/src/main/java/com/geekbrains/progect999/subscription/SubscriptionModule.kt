package com.geekbrains.progect999.subscription

import com.geekbrains.progect999.core.ClearRepresentative
import com.geekbrains.progect999.core.Module
import com.geekbrains.progect999.core.ProvideSharedPreferences
import com.geekbrains.progect999.main.UserPremiumCache

class SubscriptionModule(
    private val core:ProvideSharedPreferences.Core,
    private val clear:ClearRepresentative
) : Module<SubscriptionRepresentative> {
    override fun representative() = SubscriptionRepresentative.Base(
        clear,
        UserPremiumCache.Base(core.sharedPreferences()),
        core.navigation()
    )
}