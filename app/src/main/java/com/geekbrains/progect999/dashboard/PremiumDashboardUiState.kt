package com.geekbrains.progect999.dashboard

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.geekbrains.progect999.core.CustomButton
import com.geekbrains.progect999.core.CustomTextView
import com.geekbrains.progect999.core.HideInShow

interface PremiumDashboardUiState {
    fun show(button: HideInShow,textView: HideInShow)
    object Playing : PremiumDashboardUiState {
        override fun show(button: HideInShow,textView: HideInShow) {
            button.hide()
            textView.show()
        }

    }
    object Empty : PremiumDashboardUiState {
        override fun show(button: HideInShow, textView: HideInShow) {

        }

    }
}