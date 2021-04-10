package com.example.qtracker

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_QTracker)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}