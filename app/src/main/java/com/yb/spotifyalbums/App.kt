package com.yb.spotifyalbums

import android.app.Application
import com.yb.spotifyalbums.di.AppComponent
import com.yb.spotifyalbums.di.DaggerAppComponent
import timber.log.Timber

open class App : Application() {

    open lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDi()
        initTimber()
    }

    private fun initDi() {
        appComponent = DaggerAppComponent.create()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}