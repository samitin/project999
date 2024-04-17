package com.geekbrains.progect999.subscription.data

import kotlinx.coroutines.delay

interface SubscriptionCloudDataSource {
    suspend fun subscribe()
    class Base : SubscriptionCloudDataSource {
        override suspend fun subscribe() {
            delay(10000)
        }

    }

    class MockForUiTest : SubscriptionCloudDataSource{
        override suspend fun subscribe() {

        }

    }
}