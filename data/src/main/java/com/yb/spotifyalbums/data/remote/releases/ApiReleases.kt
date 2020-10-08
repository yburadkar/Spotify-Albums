package com.yb.spotifyalbums.data.remote.releases

import com.google.gson.annotations.SerializedName
import com.yb.spotifyalbums.domain.models.AlbumImage
import com.yb.spotifyalbums.domain.models.Albums
import com.yb.spotifyalbums.domain.models.ExtUrls
import com.yb.spotifyalbums.domain.models.SimpleAlbum

class ApiReleases(
    val albums: ApiAlbums? = null
)

class ApiAlbums(
    override val items: List<ApiSimpleAlbum>? = null,
    override val offset: Int? = null,
    override val limit: Int? = null,
    override val total: Int? = null,
) : Albums

open class ApiSimpleAlbum(
    override val id: String? = null,
    @SerializedName("external_urls")
    override val externalUrls: ApiExtUrls? = null,
    override val name: String? = null,
    override val images: List<ApiAlbumImage>? = null,
    @SerializedName("release_date")
    override val releaseDate: String? = null
) : SimpleAlbum

class ApiAlbumImage(
    override val height: Int? = null,
    override val width: Int? = null,
    override val url: String? = null
) : AlbumImage

class ApiExtUrls(
    override val spotify: String? = null
) : ExtUrls