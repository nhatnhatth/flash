package com.kna.flashvibrate

import android.app.Application

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppPreferences(this)
    }
}