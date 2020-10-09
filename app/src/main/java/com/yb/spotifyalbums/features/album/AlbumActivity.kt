package com.yb.spotifyalbums.features.album

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.yb.spotifyalbums.App
import com.yb.spotifyalbums.R
import com.yb.spotifyalbums.databinding.ActivityAlbumBinding
import com.yb.spotifyalbums.di.ViewModelFactory
import com.yb.spotifyalbums.domain.models.Album
import com.yb.spotifyalbums.domain.models.Artist
import com.yb.spotifyalbums.helpers.Resource
import com.yb.spotifyalbums.helpers.ResourceStatus
import com.yb.spotifyalbums.helpers.setVisibility
import com.yb.spotifyalbums.helpers.showSnackbar
import javax.inject.Inject

class AlbumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlbumBinding
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: AlbumViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setUpToolbar()
        observeViewModel()
        if (savedInstanceState == null) {
            val id = intent.getStringExtra(KEY_ALBUM_ID)!!
            viewModel.getAlbumDetails(id)
        }
    }

    private fun setUpToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.album_details_actionbar_title)
        }
    }

    private fun observeViewModel() {
        viewModel.album.observe(this) { renderViewState(it) }
    }

    private fun renderViewState(resource: Resource<Album>?) {
        resource?.let { res ->
            showUserMessages(resource.status)
            res.data?.let {
                with(binding) {
                    Picasso.get().load(it.images?.first()?.url).into(albumCover)
                    albumName.text = it.name
                    releaseDate.text = it.releaseDate
                    albumLabel.text = it.label
                    trackCount.text = it.totalTracks.toString()
                }
                showArtists(it.artists ?: emptyList())
            }
        }
    }

    private fun showArtists(artists: List<Artist>) {
        binding.artistsLabel.setVisibility(show = artists.isNotEmpty())
        artists.forEach {
            val textView = TextView(this)
            textView.text = it.name
            binding.artistList.addView(textView)
        }
    }

    private fun showUserMessages(status: ResourceStatus) {
        with(binding) {
            loadingIndicator.setVisibility(show = status == ResourceStatus.LOADING)
            if (status == ResourceStatus.ERROR) {
                root.showSnackbar(getString(R.string.loading_error_message))
                albumContainer.setVisibility(false)
            }
        }
    }

    private fun inject() = (application as App).appComponent.inject(this)

    companion object {
        private const val KEY_ALBUM_ID = "album_id"
        fun getIntent(context: Context, albumId: String): Intent {
            return Intent(context, AlbumActivity::class.java).apply {
                putExtra(KEY_ALBUM_ID, albumId)
            }
        }
    }
}