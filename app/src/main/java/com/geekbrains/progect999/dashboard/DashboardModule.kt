package com.geekbrains.progect999.dashboard

import com.geekbrains.progect999.core.Module
import com.geekbrains.progect999.core.ProvideSharedPreferences
import com.geekbrains.progect999.main.UserPremiumCache

class DashboardModule(
    private val provideSCommonObjects: ProvideSharedPreferences.Core
) :Module<DashboardRepresentative> {
    override fun representative(): DashboardRepresentative {
        val cache = UserPremiumCache.Base(provideSCommonObjects.sharedPreferences())
        return if (cache.isUserPremium()){
            DashboardRepresentative.Premium(PremiumDashboardObservable.Base())
        }else{
            DashboardRepresentative.Base(provideSCommonObjects.navigation())
        }
    }
}