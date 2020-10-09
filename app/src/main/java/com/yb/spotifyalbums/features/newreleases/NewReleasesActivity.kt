package com.yb.spotifyalbums.features.newreleases

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yb.spotifyalbums.App
import com.yb.spotifyalbums.R
import com.yb.spotifyalbums.databinding.ActivityNewReleasesBinding
import com.yb.spotifyalbums.di.ViewModelFactory
import com.yb.spotifyalbums.domain.models.SimpleAlbum
import com.yb.spotifyalbums.helpers.Resource
import com.yb.spotifyalbums.helpers.ResourceStatus
import com.yb.spotifyalbums.models.UiAlbum
import javax.inject.Inject

class NewReleasesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewReleasesBinding
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: NewReleasesViewModel by viewModels { viewModelFactory }
    private lateinit var albumAdapter: AlbumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        binding = ActivityNewReleasesBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setUpViews()
        observeViewModel()
        viewModel.loadNewReleases()
    }

    private fun observeViewModel() {
        viewModel.releases.observe(this) {
            renderViewState(it)
        }
    }

    private fun renderViewState(resource: Resource<List<SimpleAlbum>>?) {
        resource?.let { res ->
            showUserMessages(res.status)
            albumAdapter.submitList(res.data?.map { UiAlbum.from(it) })
        }
    }

    private fun showUserMessages(status: ResourceStatus) {
        if (status == ResourceStatus.ERROR) showSnackBar(getString(R.string.loading_error_message))
        with(binding) {
            srlAlbums.isRefreshing = status == ResourceStatus.LOADING
        }
    }

    private fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(binding.root, message, duration).show()
    }

    private fun setUpViews() {
        albumAdapter = AlbumAdapter(
            shareAction = {
                //TODO
            },
            albumClickAction = {
                //TODO
            }
        )
        with(binding) {
            rvAlbums.apply {
                adapter = albumAdapter
                layoutManager = LinearLayoutManager(this@NewReleasesActivity)
                setHasFixedSize(true)
            }
            srlAlbums.setOnRefreshListener { viewModel.reloadReleases() }
        }
    }

    private fun inject() {
        (application as App).appComponent.inject(this)
    }
}