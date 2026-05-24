package com.popflix

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PopflixApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
