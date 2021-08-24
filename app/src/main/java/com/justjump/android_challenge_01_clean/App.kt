package com.justjump.android_challenge_01_clean

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        initdi()
    }
}