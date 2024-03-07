package com.geekbrains.progect999.main

import com.geekbrains.progect999.core.Module
import com.geekbrains.progect999.core.ProvideSharedPreferences

class MainModule(private val core : ProvideSharedPreferences.Core) : Module<MainRepresentative> {
    override fun representative() = MainRepresentative.Base(core.navigation())//todo core module

}