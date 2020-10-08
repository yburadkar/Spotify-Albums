package com.yb.spotifyalbums.domain.models

interface Album {
    val artists: List<Artist>?
    val label: String?
    val totalTracks: Int
}

interface Artist {
    val externalUrls: ExtUrls?
    val name: String?
}