package com.example.mining.preferencedata

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MiningPreferenceData", Context.MODE_PRIVATE)

    fun put(key: String?, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String?): String? {
        return sharedPreferences.getString(key, null)
    }

    companion object {
        @Volatile
        private var instance: SharedPreferenceHelper? = null

        fun getInstance(context: Context): SharedPreferenceHelper =
            instance ?: synchronized(this) {
                instance ?: SharedPreferenceHelper(context).also { instance = it }
            }

        fun getInstance(): SharedPreferenceHelper {
            return instance ?: throw IllegalArgumentException("Init with getInstance(Context)")
        }
    }
}