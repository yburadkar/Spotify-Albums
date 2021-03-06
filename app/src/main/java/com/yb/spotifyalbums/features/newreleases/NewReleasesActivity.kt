package com.yb.spotifyalbums.features.newreleases

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yb.spotifyalbums.App
import com.yb.spotifyalbums.R
import com.yb.spotifyalbums.databinding.ActivityNewReleasesBinding
import com.yb.spotifyalbums.di.ViewModelFactory
import com.yb.spotifyalbums.domain.models.SimpleAlbum
import com.yb.spotifyalbums.features.album.AlbumActivity
import com.yb.spotifyalbums.helpers.Resource
import com.yb.spotifyalbums.helpers.ResourceStatus
import com.yb.spotifyalbums.helpers.showSnackbar
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
        if (savedInstanceState == null) viewModel.loadNewReleases()
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
        with(binding) {
            if (status == ResourceStatus.ERROR) root.showSnackbar(getString(R.string.loading_error_message))
            srlAlbums.isRefreshing = status == ResourceStatus.LOADING
        }
    }

    private fun setUpViews() {
        albumAdapter = AlbumAdapter(
            shareAction = { shareAlbumLink(it) },
            albumClickAction = { startActivity(AlbumActivity.getIntent(this, it)) },
            scrollToEndAction = { viewModel.loadNewReleases() }
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

    private fun shareAlbumLink(albumUrl: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, albumUrl)
        }
        val chooser = Intent.createChooser(intent, "Share Album link")
        if (intent.resolveActivity(packageManager) != null) startActivity(chooser)
    }

    private fun inject() {
        (application as App).appComponent.inject(this)
    }
}