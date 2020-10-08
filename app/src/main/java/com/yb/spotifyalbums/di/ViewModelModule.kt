package com.yb.spotifyalbums.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yb.spotifyalbums.features.newreleases.NewReleasesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NewReleasesViewModel::class)
    abstract fun bindNewReleasesViewModel(viewModel: NewReleasesViewModel): ViewModel

}