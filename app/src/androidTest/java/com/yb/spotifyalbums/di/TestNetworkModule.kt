package com.yb.spotifyalbums.di

import com.yb.spotifyalbums.data.remote.AlbumService
import com.yb.spotifyalbums.data.remote.NewReleasesService
import com.yb.spotifyalbums.data.repos.FakeAlbumRepo
import com.yb.spotifyalbums.domain.repos.IAlbumRepository
import com.yb.spotifyalbums.domain.repos.INewReleasesRepository
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class TestNetworkModule(
    private val retrofit: Retrofit = mockk(),
    private val newReleasesService: NewReleasesService = mockk(),
    private val albumService: AlbumService = mockk(),
    private val newReleasesRepository: INewReleasesRepository = mockk(),
    private val albumRepository: IAlbumRepository = FakeAlbumRepo()
) {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = retrofit

    @Singleton
    @Provides
    fun provideNewReleasesService(): NewReleasesService = newReleasesService

    @Singleton
    @Provides
    fun provideAlbumService(): AlbumService = albumService

    @Singleton
    @Provides
    fun provideNewReleasesRepository(): INewReleasesRepository = newReleasesRepository

    @Singleton
    @Provides
    fun provideAlbumRepository(): IAlbumRepository = albumRepository

}