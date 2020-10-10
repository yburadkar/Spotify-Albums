package com.yb.spotifyalbums.data.repos

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yb.spotifyalbums.data.remote.album.ApiAlbum
import com.yb.spotifyalbums.domain.models.Album
import com.yb.spotifyalbums.domain.repos.IAlbumRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class FakeAlbumRepo : IAlbumRepository {
    private val json = """
        [
          {"artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/5YA1c6yVkPnflTLMfOgjzc"},"name":"Vistas"}],"label":"Retrospect Records","total_tracks":2,"external_urls":{"spotify":"https://open.spotify.com/album/1vpkXxcn8dnyKLWhpJvtKN"},"id":"1vpkXxcn8dnyKLWhpJvtKN","images":[{"height":640,"url":"https://i.scdn.co/image/ab67616d0000b273842f32358ebc8998fd2aa90e","width":640},{"height":300,"url":"https://i.scdn.co/image/ab67616d00001e02842f32358ebc8998fd2aa90e","width":300},{"height":64,"url":"https://i.scdn.co/image/ab67616d00004851842f32358ebc8998fd2aa90e","width":64}],"name":"Spotify Singles","release_date":"2020-10-07"},
          {"artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/6UCQYrcJ6wab6gnQ89OJFh"},"name":"Headie One"}],"label":"Relentless Records","total_tracks":20,"external_urls":{"spotify":"https://open.spotify.com/album/066XCkY2VUxaJHf5g3tHJx"},"id":"066XCkY2VUxaJHf5g3tHJx","images":[{"height":640,"url":"https://i.scdn.co/image/ab67616d0000b273c1987eca084d9ac1b67d265d","width":640},{"height":300,"url":"https://i.scdn.co/image/ab67616d00001e02c1987eca084d9ac1b67d265d","width":300},{"height":64,"url":"https://i.scdn.co/image/ab67616d00004851c1987eca084d9ac1b67d265d","width":64}],"name":"EDNA","release_date":"2020-10-09"},
          {"artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/5VadK1havLhK1OpKYsXv9y"},"name":"D-Block Europe"}],"label":"D-Block Europe","total_tracks":29,"external_urls":{"spotify":"https://open.spotify.com/album/0sIKtW6fcbsN5QKh537iQ5"},"id":"0sIKtW6fcbsN5QKh537iQ5","images":[{"height":640,"url":"https://i.scdn.co/image/ab67616d0000b2734376f0c4c69a7a333219b1ae","width":640},{"height":300,"url":"https://i.scdn.co/image/ab67616d00001e024376f0c4c69a7a333219b1ae","width":300},{"height":64,"url":"https://i.scdn.co/image/ab67616d000048514376f0c4c69a7a333219b1ae","width":64}],"name":"The Blue Print – Us Vs. Them","release_date":"2020-10-09"},
          {"artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/28EyduqESEOVMO6vglvaUZ"},"name":"Joesef"}],"label":"Bold Cut","total_tracks":6,"external_urls":{"spotify":"https://open.spotify.com/album/5UumZ7WIf4xAJyv4OZHafI"},"id":"5UumZ7WIf4xAJyv4OZHafI","images":[{"height":640,"url":"https://i.scdn.co/image/ab67616d0000b2736c03b4710dda3e9fb973ced6","width":640},{"height":300,"url":"https://i.scdn.co/image/ab67616d00001e026c03b4710dda3e9fb973ced6","width":300},{"height":64,"url":"https://i.scdn.co/image/ab67616d000048516c03b4710dda3e9fb973ced6","width":64}],"name":"Does It Make You Feel Good?","release_date":"2020-10-09"},
          {"artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/0QaSiI5TLA4N7mcsdxShDO"},"name":"Sub Focus"},{"external_urls":{"spotify":"https://open.spotify.com/artist/6m8itYST9ADjBIYevXSb1r"},"name":"Wilkinson"}],"label":"EMI","total_tracks":11,"external_urls":{"spotify":"https://open.spotify.com/album/6SC0Omssa5QQtX22zlZGEG"},"id":"6SC0Omssa5QQtX22zlZGEG","images":[{"height":640,"url":"https://i.scdn.co/image/ab67616d0000b273c7a8b1e74ccc6833cdb48d17","width":640},{"height":300,"url":"https://i.scdn.co/image/ab67616d00001e02c7a8b1e74ccc6833cdb48d17","width":300},{"height":64,"url":"https://i.scdn.co/image/ab67616d00004851c7a8b1e74ccc6833cdb48d17","width":64}],"name":"Portals","release_date":"2020-10-09"},
          {"artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/0xBhUB0EfzvchYnaIWkdBw"},"name":"Ward Thomas"}],"label":"WTW Music/Eastwest","total_tracks":14,"external_urls":{"spotify":"https://open.spotify.com/album/4sb0AxaFWzJccdArYLTLKQ"},"id":"4sb0AxaFWzJccdArYLTLKQ","images":[{"height":640,"url":"https://i.scdn.co/image/ab67616d0000b273547965da4addc59104ffb936","width":640},{"height":300,"url":"https://i.scdn.co/image/ab67616d00001e02547965da4addc59104ffb936","width":300},{"height":64,"url":"https://i.scdn.co/image/ab67616d00004851547965da4addc59104ffb936","width":64}],"name":"Invitation","release_date":"2020-10-09"},
          {"artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/7ltW5jYRnGOE4O1vcgW2DI"},"name":"MORGAN"}],"label":"Major Tom\u0027s Records","total_tracks":5,"external_urls":{"spotify":"https://open.spotify.com/album/31UtLq65wdYZIowQoTTydD"},"id":"31UtLq65wdYZIowQoTTydD","images":[{"height":640,"url":"https://i.scdn.co/image/ab67616d0000b273980fc37cbe345a493156f379","width":640},{"height":300,"url":"https://i.scdn.co/image/ab67616d00001e02980fc37cbe345a493156f379","width":300},{"height":64,"url":"https://i.scdn.co/image/ab67616d00004851980fc37cbe345a493156f379","width":64}],"name":"Alien","release_date":"2020-10-09"}
        ]
        """.trimIndent().replace("\n", "")
    private val type = object : TypeToken<List<ApiAlbum>>() {}.type
    private val albums = Gson().fromJson<List<ApiAlbum>>(json.trimIndent(), type)

    override fun getAlbumDetails(id: String): Single<Album> {
        val album: Album? = albums.find { it.id == id }
        return (album?.let { Single.just(it) } ?: Single.error(Throwable("Album not found")))
            .delay(1, TimeUnit.SECONDS, Schedulers.trampoline(), true)
    }

    fun getAlbumsById(id: String): Album? = albums.find { it.id == id }

}