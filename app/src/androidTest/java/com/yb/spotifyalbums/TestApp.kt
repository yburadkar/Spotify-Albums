package com.yb.spotifyalbums

import com.yb.spotifyalbums.di.AppComponent
import com.yb.spotifyalbums.di.DaggerTestAppComponent

class TestApp : App() {

    override lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestAppComponent.create()
    }

}