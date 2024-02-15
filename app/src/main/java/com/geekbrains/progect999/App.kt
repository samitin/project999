package com.geekbrains.progect999

import android.app.Application
import android.util.Log

class App : Application() {

    private val handleDeath = HandleDeath.Base()
    lateinit var mainRepresentative:MainRepresentative
    override fun onCreate() {
        super.onCreate()
        mainRepresentative = MainRepresentative.Base(UiObservable.Single())
        val processId = android.os.Process.myPid()
        log("app onCreate $processId")
    }
    fun activityCreated(firstOpening : Boolean){
        if (firstOpening){
            handleDeath.firstOpening()
        }else{
            if (handleDeath.wasDeathHappened()){
                log("death happened смерть случилась")
                handleDeath.deathHandled()
            }else{
                log("just activity recreate просто воссоздание активности")
            }
        }
    }
    private fun log(msg : String){
        Log.d(TAG,msg)
    }
    companion object{
        const val TAG = "jsc91"
    }
}