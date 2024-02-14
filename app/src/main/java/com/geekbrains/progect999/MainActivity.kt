package com.geekbrains.progect999

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).activityCreated(savedInstanceState == null)
        val textView = findViewById<TextView>(R.id.counterText)

        if (savedInstanceState == null)
            textView.text = "0"

        textView.setOnClickListener{
            var count = textView.text.toString().toInt()
            textView.text = (++count).toString()
        }

    }
}
