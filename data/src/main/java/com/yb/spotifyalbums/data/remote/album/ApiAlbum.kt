package com.yb.spotifyalbums.data.remote.album

import com.google.gson.annotations.SerializedName
import com.yb.spotifyalbums.data.remote.releases.ApiExtUrls
import com.yb.spotifyalbums.data.remote.releases.ApiSimpleAlbum
import com.yb.spotifyalbums.domain.models.Album
import com.yb.spotifyalbums.domain.models.Artist

class ApiAlbum(
    override val artists: List<ApiArtist>? = null,
    override val label: String? = null,
    @SerializedName("total_tracks")
    override val totalTracks: Int
) : ApiSimpleAlbum(), Album

class ApiArtist(
    @SerializedName("external_urls")
    override val externalUrls: ApiExtUrls? = null,
    override val name: String? = null
) : Artist
