package com.yb.spotifyalbums.data.remote

import com.yb.spotifyalbums.data.BuildConfig
import com.yb.spotifyalbums.data.remote.album.ApiAlbum
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AlbumService {

    @GET("v1/albums/{id}")
    fun getAlbumDetails(
        @Header("Authorization") token: String = "Bearer ${BuildConfig.SPOTIFY_OAUTH_TOKEN}",
        @Path("id") albumId: String
    ): Single<ApiAlbum>

}