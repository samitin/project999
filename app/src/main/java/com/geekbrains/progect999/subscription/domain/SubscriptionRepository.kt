package com.geekbrains.progect999.subscription.domain

interface SubscriptionRepository{
    fun isPremiumUser() : Boolean
     fun subscribe()
     suspend fun subscribeInternal()

}