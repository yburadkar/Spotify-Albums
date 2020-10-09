package com.yb.spotifyalbums.features.newreleases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yb.spotifyalbums.domain.models.SimpleAlbum
import com.yb.spotifyalbums.domain.repos.INewReleasesRepository
import com.yb.spotifyalbums.helpers.DisposingViewModel
import com.yb.spotifyalbums.helpers.Resource
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class NewReleasesViewModel @Inject constructor(
    private val newReleasesRepo: INewReleasesRepository,
    @Named("io") private val io: Scheduler,
    @Named("ui") private val ui: Scheduler
) : DisposingViewModel() {

    private val _releases = MutableLiveData<Resource<MutableList<SimpleAlbum>>>()
    val releases: LiveData<Resource<MutableList<SimpleAlbum>>> = _releases
    private var offset = 0
    private val limit = 20
    private var total = limit

    init {
        _releases.value = Resource.success(mutableListOf())
    }

    fun loadNewReleases(country: String = "GB") {
        if(offset + limit <= total)
        newReleasesRepo.getNewReleases(country, offset, limit)
            .doOnSubscribe { _releases.postValue(Resource.loading(_releases.value?.data)) }
            .subscribeOn(io)
            .observeOn(ui)
            .subscribeBy(
                onError = {
                    _releases.value = Resource.error(data = _releases.value?.data, error = it)
                },
                onSuccess = {
                    val items = it.value?.items ?: emptyList()
                    Timber.d("Received ${items.size} albums")
                    _releases.value = Resource.success(data = _releases.value?.data?.apply { addAll(items) })
                    it.value?.let { albums ->
                        total = albums.total ?: limit
                        offset += albums.items?.size ?: 0
                        Timber.d("offset = $offset, limit = $limit, total = $total")
                    }
                }
            ).addTo(disposables)
    }

    fun reloadReleases() {
        clearAllAlbums()
        loadNewReleases()
    }

    private fun clearAllAlbums() {
        _releases.value?.data?.clear()
        offset = 0
    }
}