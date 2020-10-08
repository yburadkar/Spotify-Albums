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
import javax.inject.Inject
import javax.inject.Named

class NewReleasesViewModel @Inject constructor(
    private val newReleasesRepo: INewReleasesRepository,
    @Named("io") private val io: Scheduler,
    @Named("ui") private val ui: Scheduler
) : DisposingViewModel() {

    private val _releases = MutableLiveData<Resource<List<SimpleAlbum>>>()
    val releases: LiveData<Resource<List<SimpleAlbum>>> = _releases
    private var offset = 0
    private val limit = 20

    init {
        _releases.value = Resource.success(emptyList())
    }

    fun loadNewReleases(country: String = "GB") {
        newReleasesRepo.getNewReleases(country, offset, limit)
            .doOnSubscribe { _releases.postValue(Resource.loading(_releases.value?.data)) }
            .subscribeOn(io)
            .observeOn(ui)
            .subscribeBy(
                onError = {
                    _releases.value = Resource.error(data = _releases.value?.data, error = it)
                },
                onSuccess = {
                    _releases.value = Resource.success(data = it.value?.items)
                }
            ).addTo(disposables)
    }

    fun reloadReleases() {
        offset = 0
        loadNewReleases()
    }
}