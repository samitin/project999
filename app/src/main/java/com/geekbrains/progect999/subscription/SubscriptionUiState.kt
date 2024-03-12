package com.geekbrains.progect999.subscription

import android.util.Log
import android.widget.ProgressBar
import com.geekbrains.progect999.core.HideInShow
import java.io.Serializable

interface SubscriptionUiState : Serializable{
    fun observed(representative: SubscriptionObserved) = representative.observed()
    fun restoreAfterDeath(
        representative: SubscriptionInner,
        observable: SubscriptionObservable
    ) = observable.update(this)
    fun show( subscribeButton : HideInShow, progressBar: HideInShow, finishButton : HideInShow)
    object Initial : SubscriptionUiState {
        override fun show(subscribeButton: HideInShow, progressBar: HideInShow, finishButton: HideInShow) {
            subscribeButton.show()
            progressBar.hide()
            finishButton.hide()
        }
    }

    object Loading : SubscriptionUiState{
        override fun show(subscribeButton: HideInShow, progressBar: HideInShow, finishButton: HideInShow) {
            subscribeButton.hide()
            progressBar.show()
            finishButton.hide()
        }

        override fun restoreAfterDeath(
            representative: SubscriptionInner,
            observable: SubscriptionObservable
        ) {
            Log.d("jsc91","LoadingUiState#subscribeInner")
            representative.subscribeInner()
        }

        override fun observed(representative: SubscriptionObserved) = Unit
    }
    object Success : SubscriptionUiState{

        override fun show(subscribeButton: HideInShow, progressBar: HideInShow, finishButton: HideInShow) {
            subscribeButton.hide()
            progressBar.hide()
            finishButton.show()
        }
    }
    object Empty : SubscriptionUiState {
        override fun show(
            subscribeButton: HideInShow,
            progressBar: HideInShow,
            finishButton: HideInShow
        ) {

        }

        override fun restoreAfterDeath(
            representative: SubscriptionInner,
            observable: SubscriptionObservable
        ) {
            representative.subscribeInner()}
    }
}