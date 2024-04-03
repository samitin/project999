package com.geekbrains.progect999.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.geekbrains.progect999.dashboard.DashboardFragment
import com.geekbrains.progect999.subscription.SubscriptionFragment

interface Screen {
    fun observed(representative: MainRepresentative) = representative
    fun show(fragmentManager: FragmentManager,conteinerId:Int)
    abstract class Add(private val fragmentClass: Class<out Fragment>) : Screen{
        override fun show(fragmentManager: FragmentManager, conteinerId: Int) {
           fragmentManager.beginTransaction()
               .replace(conteinerId,fragmentClass.getDeclaredConstructor().newInstance())
               .addToBackStack(fragmentClass.name)
               .commit()
        }
    }
    abstract class Replace(private val fragmentClass: Class<out Fragment>) : Screen{
        override fun show(fragmentManager: FragmentManager, conteinerId: Int) {
            fragmentManager.beginTransaction()
                .replace(conteinerId,fragmentClass.getDeclaredConstructor().newInstance())
                .commit()
        }
    }
    object Pop : Screen{
        override fun show(fragmentManager: FragmentManager, conteinerId: Int) {
            fragmentManager.popBackStack()
        }

    }
    object Empty : Screen{
        override fun show(fragmentManager: FragmentManager, conteinerId: Int) {

        }

    }
    object Dashboard : Replace(DashboardFragment::class.java)
    object Subscription : Add(SubscriptionFragment::class.java)
}