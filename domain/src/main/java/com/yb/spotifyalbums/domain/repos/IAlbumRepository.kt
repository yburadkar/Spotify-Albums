package com.yb.spotifyalbums.domain.repos

import com.yb.spotifyalbums.domain.models.Album
import io.reactivex.Single

interface IAlbumRepository {
    fun getAlbumDetails(id: String): Single<Album>
}