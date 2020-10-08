package com.yb.spotifyalbums.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    @Named("io")
    fun provideIoScheduler(): Scheduler = Schedulers.io()

    @Singleton
    @Provides
    @Named("ui")
    fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()

}