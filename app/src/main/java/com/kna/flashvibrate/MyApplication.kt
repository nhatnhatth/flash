package com.kna.flashvibrate

import android.app.Application
import com.kna.flashvibrate.data.db.RoomDatabase

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppPreferences(this)
        RoomDatabase.initDatabase(this)
    }
}