package com.geekbrains.progect999.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment<T:Representative<*>>(@LayoutRes layoutId : Int) : Fragment(layoutId){
    lateinit var representative: T
    protected abstract val clazz : Class<T>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        representative = (requireActivity() as ProvideRepresentative).provideRepresentative(
            clazz)
    }

}