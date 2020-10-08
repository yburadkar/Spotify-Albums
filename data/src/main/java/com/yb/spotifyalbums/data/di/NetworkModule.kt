package com.yb.spotifyalbums.data.di

import com.yb.spotifyalbums.data.remote.AlbumService
import com.yb.spotifyalbums.data.remote.NewReleasesService
import com.yb.spotifyalbums.data.remote.repos.NewReleasesRepository
import com.yb.spotifyalbums.domain.INewReleasesRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideNewReleasesService(retrofit: Retrofit): NewReleasesService = retrofit.create(NewReleasesService::class.java)

    @Singleton
    @Provides
    fun provideAlbumService(retrofit: Retrofit): AlbumService = retrofit.create(AlbumService::class.java)

    @Singleton
    @Provides
    fun provideNewReleasesRepository(newReleasesRepository: NewReleasesRepository): INewReleasesRepository = newReleasesRepository

    companion object {
        private const val BASE_URL = "https://api.spotify.com/"
    }
}