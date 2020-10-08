package com.yb.spotifyalbums.di

import com.yb.spotifyalbums.MainActivity
import com.yb.spotifyalbums.data.di.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

}