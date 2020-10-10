package com.yb.spotifyalbums.data.models

import com.yb.spotifyalbums.domain.models.Album
import com.yb.spotifyalbums.domain.models.AlbumImage
import com.yb.spotifyalbums.domain.models.Artist
import com.yb.spotifyalbums.domain.models.ExtUrls
import com.yb.spotifyalbums.domain.models.SimpleAlbum

data class TestAlbum(
    override val id: String? = null,
    override val externalUrls: TestExtUrls? = null,
    override val name: String? = null,
    override val images: List<TestAlbumImage>? = null,
    override val releaseDate: String? = null,
    override val artists: List<TestArtist>? = null,
    override val label: String? = null,
    override val totalTracks: Int
) : Album {
    companion object {
        fun from(album: Album): TestAlbum {
            return with(album) {
                TestAlbum(
                    id = album.id,
                    externalUrls = album.externalUrls?.let { TestExtUrls.from(it) },
                    name = album.name,
                    images = album.images?.map { TestAlbumImage.from(it) },
                    releaseDate = album.releaseDate,
                    artists = album.artists?.map { TestArtist.from(it) },
                    label = album.label,
                    totalTracks = album.totalTracks
                )
            }
        }
    }
}

data class TestSimpleAlbum(
    override val id: String? = null,
    override val externalUrls: TestExtUrls? = null,
    override val name: String? = null,
    override val images: List<TestAlbumImage>? = null,
    override val releaseDate: String? = null
) : SimpleAlbum {
    companion object {
        fun from(simpleAlbum: SimpleAlbum): TestSimpleAlbum {
            return TestSimpleAlbum(
                id = simpleAlbum.id,
                externalUrls = simpleAlbum.externalUrls?.let { TestExtUrls.from(it) },
                name = simpleAlbum.name,
                images = simpleAlbum.images?.map { TestAlbumImage.from(it) },
                releaseDate = simpleAlbum.releaseDate
            )
        }
    }
}

data class TestAlbumImage(
    override val height: Int? = null,
    override val width: Int? = null,
    override val url: String? = null
) : AlbumImage {
    companion object {
        fun from(image: AlbumImage): TestAlbumImage {
            return TestAlbumImage(
                height = image.height,
                width = image.width,
                url = image.url
            )
        }
    }
}

data class TestArtist(
    override val externalUrls: TestExtUrls? = null,
    override val name: String? = null
) : Artist {
    companion object {
        fun from(artist: Artist): TestArtist {
            return TestArtist(
                externalUrls = artist.externalUrls?.let { TestExtUrls.from(it) },
                name = artist.name
            )
        }
    }
}

data class TestExtUrls(
    override val spotify: String? = null
) : ExtUrls {
    companion object {
        fun from(urls: ExtUrls): TestExtUrls {
            return TestExtUrls(spotify = urls.spotify)
        }
    }
}
