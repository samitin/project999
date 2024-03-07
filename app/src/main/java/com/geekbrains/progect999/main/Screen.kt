package com.geekbrains.progect999.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.geekbrains.progect999.dashboard.DashboardFragment
import com.geekbrains.progect999.subscription.SubscriptionFragment

interface Screen {
    fun show(fragmentManager: FragmentManager,conteinerId:Int)
    abstract class Add(private val fragmentClass: Class<out Fragment>) : Screen{
        override fun show(fragmentManager: FragmentManager, conteinerId: Int) {
           fragmentManager.beginTransaction()
               .replace(conteinerId,fragmentClass.newInstance())
               .addToBackStack(fragmentClass.name)
               .commit()
        }
    }
    abstract class Replace(private val fragmentClass: Class<out Fragment>) : Screen{
        override fun show(fragmentManager: FragmentManager, conteinerId: Int) {
            fragmentManager.beginTransaction()
                .replace(conteinerId,fragmentClass.newInstance())
                .commit()
        }
    }
    object Dashboard : Replace(DashboardFragment::class.java)
    object Subscription : Add(SubscriptionFragment::class.java)
}