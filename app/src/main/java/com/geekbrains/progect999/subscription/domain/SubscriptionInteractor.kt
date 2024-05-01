package com.geekbrains.progect999.subscription.domain

import com.geekbrains.progect999.main.UserPremiumCache
import com.geekbrains.progect999.subscription.presentation.SubscriptionUiState
import kotlinx.coroutines.delay

interface SubscriptionInteractor {
    suspend fun subscribe() : SubscriptionResult
    suspend fun subscribeInternal() : SubscriptionResult
    class Base(private val repository: SubscriptionRepository
    ) : SubscriptionInteractor {
        override suspend fun subscribe() : SubscriptionResult{
            if (repository.isPremiumUser())
                return SubscriptionResult.Success
            else{
                repository.subscribe()
                return SubscriptionResult.NoDataYet
            }

        }

        override suspend fun subscribeInternal(): SubscriptionResult {
            repository.subscribeInternal()
            return SubscriptionResult.Success
        }
    }
}
interface SubscriptionResult{
    interface Mapper{
        fun mapSuccess(canGoBackCallBack:(Boolean) -> Unit)
    }
    fun map(mapper:Mapper,canGoBackCallBack:(Boolean) -> Unit)
    object Success : SubscriptionResult{
        override fun map(mapper: Mapper,canGoBackCallBack:(Boolean) -> Unit) = mapper.mapSuccess(canGoBackCallBack)
    }
    object NoDataYet : SubscriptionResult {
        override fun map(mapper: Mapper,canGoBackCallBack:(Boolean) -> Unit) = Unit
    }
}