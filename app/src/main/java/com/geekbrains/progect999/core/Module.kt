package com.geekbrains.progect999.core

interface Module< T : Representative<*>> {
    fun representative() : T
}