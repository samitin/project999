package com.geekbrains.progect999.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geekbrains.progect999.core.App
import com.geekbrains.progect999.R
import com.geekbrains.progect999.core.ProvideRepresentative
import com.geekbrains.progect999.core.Representative
import com.geekbrains.progect999.core.UiObserver

class MainActivity : AppCompatActivity() ,ProvideRepresentative{

    private lateinit var representative : MainRepresentative
    private lateinit var activityCallBack : ActivityCallBack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityCallBack = object : ActivityCallBack {
            override fun update(data:Screen){
                data.show(supportFragmentManager,R.id.container)
            }
        }
        representative = provideRepresentative(MainRepresentative::class.java)
        representative.showDashboard(savedInstanceState == null)
        //getSharedPreferences()

        //(application as App).activityCreated(savedInstanceState == null)

    }

    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(activityCallBack)
    }

    override fun onPause() {
        super.onPause()
        representative.stopGettingUpdates()
    }

    override fun <T : Representative<*>> provideRepresentative(clazz: Class<T>): T =
        (application as ProvideRepresentative).provideRepresentative(clazz)



}


interface ActivityCallBack : UiObserver<Screen>{
    override fun isEmpty(): Boolean = false
}
