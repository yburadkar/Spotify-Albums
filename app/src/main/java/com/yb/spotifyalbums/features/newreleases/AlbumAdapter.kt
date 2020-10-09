package com.yb.spotifyalbums.features.newreleases

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yb.spotifyalbums.databinding.ItemAlbumBinding
import com.yb.spotifyalbums.models.UiAlbum

class AlbumAdapter(
    private val shareAction: (String) -> Unit,
    private val albumClickAction: (String) -> Unit,
    private val scrollToEndAction: () -> Unit
) : ListAdapter<UiAlbum, AlbumAdapter.AlbumViewHolder>(
    AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
        if (currentList.size - position == 5) scrollToEndAction.invoke()
    }

    inner class AlbumViewHolder(
        private val binding: ItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: UiAlbum) {
            with(binding) {
                album.imageUrl?.let { Picasso.get().load(it).into(thumbnail) }
                name.text = album.name
                releaseDate.text = album.releaseDate
                val pos = "#${adapterPosition + 1}"
                position.text = pos
                share.setOnClickListener { album.shareUrl?.let { shareAction.invoke(it) } }
                root.setOnClickListener { album.id?.let { albumClickAction.invoke(it) } }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UiAlbum>() {

            override fun areItemsTheSame(oldItem: UiAlbum, newItem: UiAlbum): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UiAlbum, newItem: UiAlbum): Boolean = oldItem == newItem

        }
    }

}