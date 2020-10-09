package com.yb.spotifyalbums.data.remote

import com.yb.spotifyalbums.data.BuildConfig
import com.yb.spotifyalbums.data.remote.releases.ApiReleases
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewReleasesService {

    @GET("v1/browse/new-releases")
    fun getNewAlbumReleases(
        @Header("Authorization") token: String = "Bearer ${BuildConfig.SPOTIFY_OAUTH_TOKEN}",
        @Query("country") country: String = "GB",
        @Query("offset") offset: String = "0",
        @Query("limit") limit: String = "20",
    ): Single<ApiReleases>

}