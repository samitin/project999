package com.geekbrains.progect999.main

import com.geekbrains.progect999.core.UiObservable
import com.geekbrains.progect999.core.UiUpdate
import com.geekbrains.progect999.core.UpdateObserver

interface Navigation {
    interface Update : UiUpdate<Screen>
    interface Observe : UpdateObserver<Screen>
    interface Mutable : Update,Observe{
        fun clear()
    }

    object Base : UiObservable.Base<Screen>(Screen.Empty),Mutable
}