package com.example.isvirin.skbktestapp.data

import android.content.Context

class SharedPreferencesStore(val context: Context) {

    fun writeLong(name: String, key: String, value: Long) {
        context.getSharedPreferences(name, Context.MODE_PRIVATE).edit().putLong(key, value).apply()
    }

    fun readLong(name: String, key: String): Long? =
            context.getSharedPreferences(name, Context.MODE_PRIVATE).getLong(key, 0L)

}