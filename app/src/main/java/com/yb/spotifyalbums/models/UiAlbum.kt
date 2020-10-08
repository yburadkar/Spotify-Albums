package com.yb.spotifyalbums.models

import com.yb.spotifyalbums.domain.models.AlbumImage
import com.yb.spotifyalbums.domain.models.SimpleAlbum

data class UiAlbum(
    val id: String? = null,
    val shareUrl: String? = null,
    val name: String? = null,
    val imageUrl: String? = null,
    val releaseDate: String? = null
) {
    companion object {
        fun from(album: SimpleAlbum): UiAlbum {
            return UiAlbum(
                id = album.id,
                shareUrl = album.externalUrls?.spotify,
                name = album.name,
                imageUrl = getThumbnailUrl(album.images),
                releaseDate = album.releaseDate
            )
        }

        private fun getThumbnailUrl(images: List<AlbumImage>?): String? {
            return images?.findLast { image ->
                image.width?.let { it > 200 } ?: false
            }?.url
        }
    }
}
