package com.geekbrains.progect999

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var representative : MainRepresentative
    private lateinit var activityCallBack : ActivityCallBack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById<TextView>(R.id.counterText)
        if (savedInstanceState == null)
            textView.text = "0"

        activityCallBack = object : ActivityCallBack {
            override fun update(data:Int) = runOnUiThread{
                textView.setText(data)
            }

        }
        representative = (application as App).mainRepresentative
        (application as App).activityCreated(savedInstanceState == null)




        textView.setOnClickListener{
            representative.startAsync()
        }

    }

    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(activityCallBack)
    }

    override fun onPause() {
        super.onPause()
        representative.stopGettingUpdates()
    }


}


interface ActivityCallBack : UiObserver<Int>
