package com.geekbrains.progect999.subscription.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import com.geekbrains.progect999.core.CustomProgressBar
import com.geekbrains.progect999.R
import com.geekbrains.progect999.core.BaseFragment
import com.geekbrains.progect999.core.CustomButton
import com.geekbrains.progect999.core.UiObserver

class SubscriptionFragment : BaseFragment<SubscriptionRepresentative>(R.layout.fragment_subscription) {


    override val clazz = SubscriptionRepresentative::class.java

    private lateinit var observer: UiObserver<SubscriptionUiState>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val subscribeButton = view.findViewById<CustomButton>(R.id.subscribeButton)
        val finishButton = view.findViewById<CustomButton>(R.id.finishButton)
        val progressBar = view.findViewById<CustomProgressBar>(R.id.progressBar)
        subscribeButton.setOnClickListener {
            representative.subscribe()
        }
        finishButton.setOnClickListener {
            representative.finish()
        }
        observer = object : SubscriptionObserver {
            override fun update(data: SubscriptionUiState) = requireActivity().runOnUiThread {
                data.observed(representative)
               data.show(subscribeButton, progressBar, finishButton)
            }
        }
        representative.init(SaveAndRestoreSubscriptionUiState.Base(savedInstanceState))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        representative.saveState(SaveAndRestoreSubscriptionUiState.Base(outState))
    }
    override fun onResume() {
        super.onResume()
        Log.d("jsc91","Subscription onResume")
        representative.startGettingUpdates(observer)
    }

    override fun onPause() {
        super.onPause()
        Log.d("jsc91","Subscription onPause")
        representative.stopGettingUpdates()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("jsc91","Subscription onDestroy")
    }
}
interface SubscriptionObserver : UiObserver<SubscriptionUiState> {

}