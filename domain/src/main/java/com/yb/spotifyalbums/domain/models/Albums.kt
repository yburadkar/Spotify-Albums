package com.yb.spotifyalbums.domain.models

interface Albums {
    val items: List<SimpleAlbum>?
    val offset: Int?
    val limit: Int?
    val total: Int?
}

interface SimpleAlbum {
    val id: String?
    val externalUrls: ExtUrls?
    val name: String?
    val images: List<AlbumImage>?
    val releaseDate: String?
}

interface AlbumImage {
    val height: Int?
    val width: Int?
    val url: String?
}

interface ExtUrls {
    val spotify: String?
}