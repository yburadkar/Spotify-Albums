package com.yb.spotifyalbums.domain.models

interface Album: SimpleAlbum {
    val artists: List<Artist>?
    val label: String?
    val totalTracks: Int
}

interface Artist {
    val externalUrls: ExtUrls?
    val name: String?
}