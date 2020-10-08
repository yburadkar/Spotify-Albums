package com.yb.spotifyalbums.data.remote.repos

import com.yb.spotifyalbums.data.remote.AlbumService
import com.yb.spotifyalbums.domain.repos.IAlbumRepository
import com.yb.spotifyalbums.domain.models.Album
import io.reactivex.Single

class AlbumRepository(
    private val albumService: AlbumService
) : IAlbumRepository {

    override fun getAlbumDetails(id: String): Single<Album> {
        return albumService.getAlbumDetails(albumId = id).map { it }
    }

}