package com.geekbrains.progect999.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.geekbrains.progect999.R
import com.geekbrains.progect999.core.BaseFragment
import com.geekbrains.progect999.core.CustomButton
import com.geekbrains.progect999.core.CustomTextView
import com.geekbrains.progect999.core.ProvideRepresentative
import com.geekbrains.progect999.core.UiObserver

class DashboardFragment : BaseFragment<DashboardRepresentative>(R.layout.fragment_dashboard) {
    init {
        Log.d("jsc91","Dashboard init")
    }
    private lateinit var callback: UiObserver<PremiumDashboardUiState>

    override val clazz = DashboardRepresentative::class.java



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<CustomButton>(R.id.playButton)
        button.setOnClickListener {
            representative.play()
        }
        val textView = view.findViewById<CustomTextView>(R.id.showPlayingTextView)
        callback = object : DashboardObserver {
            override fun update(data: PremiumDashboardUiState) {
                data.show(button!!,textView!!)
            }
        }

    }



    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(callback)
        Log.d("jsc91","Dashboard onResume")
    }

    override fun onPause() {
        super.onPause()
        representative.stopGettingUpdates()
        Log.d("jsc91","Dashboard onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("jsc91","Dashboard onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("jsc91","Dashboard onDestroyView")
    }
}

interface DashboardObserver : UiObserver<PremiumDashboardUiState> {
    override fun isEmpty(): Boolean = false
}