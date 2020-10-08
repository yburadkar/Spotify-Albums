package com.yb.spotifyalbums.helpers

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class DisposingViewModel : ViewModel() {
    val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}