package com.yb.spotifyalbums.domain

import com.yb.spotifyalbums.domain.models.Albums
import com.yb.spotifyalbums.domain.models.Optional
import io.reactivex.Single

interface INewReleasesRepository {
    fun getNewReleases(country: String = "GB", offset: Int = 0, limit: Int = 20): Single<Optional<Albums>>
}