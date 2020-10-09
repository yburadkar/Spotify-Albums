package com.yb.spotifyalbums.data.remote.repos

import com.yb.spotifyalbums.data.remote.AlbumService
import com.yb.spotifyalbums.domain.models.Album
import com.yb.spotifyalbums.domain.repos.IAlbumRepository
import io.reactivex.Single
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumService: AlbumService
) : IAlbumRepository {

    override fun getAlbumDetails(id: String): Single<Album> {
        return albumService.getAlbumDetails(albumId = id).map { it }
    }

}