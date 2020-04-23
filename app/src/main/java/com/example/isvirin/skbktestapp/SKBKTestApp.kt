package com.example.isvirin.skbktestapp

import android.app.Application
import com.example.isvirin.skbktestapp.datasource.cache.CacheLibrary
import com.example.isvirin.skbktestapp.domain.di.injectFeature
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SKBKTestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        CacheLibrary.init(this)
        startKoin { androidContext(this@SKBKTestApp) }
    }
}