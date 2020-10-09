package com.yb.spotifyalbums.features.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yb.spotifyalbums.domain.models.Album
import com.yb.spotifyalbums.domain.repos.IAlbumRepository
import com.yb.spotifyalbums.helpers.DisposingViewModel
import com.yb.spotifyalbums.helpers.Resource
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

class AlbumViewModel @Inject constructor(
    private val albumRepo: IAlbumRepository,
    @Named("io") private val io: Scheduler,
    @Named("ui") private val ui: Scheduler
): DisposingViewModel() {

    private val _album = MutableLiveData<Resource<Album>>()
    val album : LiveData<Resource<Album>> = _album

    fun getAlbumDetails(id: String) {
        albumRepo.getAlbumDetails(id)
            .doOnSubscribe { _album.postValue(Resource.loading(null)) }
            .subscribeOn(io)
            .observeOn(ui)
            .subscribeBy(
                onError = {
                    _album.value = Resource.error(data = null, error = it)
                },
                onSuccess = {
                    _album.value = Resource.success(it)
                }
            ).addTo(disposables)
    }

}