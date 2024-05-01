package com.geekbrains.progect999.subscription.presentation

import com.geekbrains.progect999.core.UiUpdate
import com.geekbrains.progect999.subscription.domain.SubscriptionResult

class SubscriptionUiMapper (
    private val observable:UiUpdate<SubscriptionUiState>
): SubscriptionResult.Mapper {
    override fun mapSuccess(canGoBackCallBack:(Boolean) -> Unit) {
        observable.update(SubscriptionUiState.Success)
        canGoBackCallBack.invoke(true)
    }
}