package com.example.mining

import android.app.Application
import com.example.mining.preferencedata.SharedPreferenceHelper

class MiningApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferenceHelper.getInstance(this)
    }
}