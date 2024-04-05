package com.geekbrains.progect999.subscription.domain

import com.geekbrains.progect999.main.UserPremiumCache
import com.geekbrains.progect999.subscription.presentation.SubscriptionUiState

interface SubscriptionInteractor {
    fun subscribe(callBack: () -> Unit)
    class Base(
        private val userPremiumCache: UserPremiumCache.Save
    ) : SubscriptionInteractor {

        override fun subscribe(callBack: () -> Unit) {
            Thread{
                Thread.sleep(1000)
                userPremiumCache.saveUserPremium()
                callBack.invoke()
            }.start()
        }
    }
}