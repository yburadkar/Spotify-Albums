package com.yb.spotifyalbums.data.remote.repos

import com.yb.spotifyalbums.data.helpers.asOptional
import com.yb.spotifyalbums.data.remote.NewReleasesService
import com.yb.spotifyalbums.domain.INewReleasesRepository
import com.yb.spotifyalbums.domain.models.Albums
import com.yb.spotifyalbums.domain.models.Optional
import io.reactivex.Single
import javax.inject.Inject

class NewReleasesRepository @Inject constructor(
    private val newReleasesService: NewReleasesService
) : INewReleasesRepository {

    override fun getNewReleases(country: String, offset: Int, limit: Int): Single<Optional<Albums>> =
        newReleasesService.getNewAlbumReleases(country = country, offset = offset.toString(), limit = limit.toString())
            .map { it.albums.asOptional() }

}