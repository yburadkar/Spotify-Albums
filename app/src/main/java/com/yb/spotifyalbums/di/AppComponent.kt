package com.yb.spotifyalbums.di

import com.yb.spotifyalbums.features.newreleases.NewReleasesActivity
import com.yb.spotifyalbums.data.di.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(activity: NewReleasesActivity)

}