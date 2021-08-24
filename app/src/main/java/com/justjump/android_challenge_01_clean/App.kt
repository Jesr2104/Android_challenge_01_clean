package com.justjump.android_challenge_01_clean

import android.app.Application
import com.justjump.android_challenge_01_clean.koin.initDI

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}