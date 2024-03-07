package com.geekbrains.progect999.subscription

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.geekbrains.progect999.R
import com.geekbrains.progect999.core.BaseFragment
import com.geekbrains.progect999.core.ProvideRepresentative

class SubscriptionFragment : BaseFragment<SubscriptionRepresentative>(R.layout.fragment_subscription) {


    override val clazz = SubscriptionRepresentative::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.subscribeButton)
        button.setOnClickListener {
            representative.subscribe()
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d("jsc91","Subscription onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("jsc91","Subscription onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("jsc91","Subscription onDestroy")
    }
}