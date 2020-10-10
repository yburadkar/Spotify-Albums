package com.yb.spotifyalbums.features.album

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yb.spotifyalbums.data.models.TestAlbum
import com.yb.spotifyalbums.data.repos.FakeAlbumRepo
import com.yb.spotifyalbums.helpers.ResourceStatus
import com.yb.spotifyalbums.utils.getOrAwaitValue
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AlbumViewModelTest {
    private val albumRepo = FakeAlbumRepo()
    private val io: Scheduler = Schedulers.trampoline()
    private val ui: Scheduler = Schedulers.trampoline()
    private lateinit var viewModel: AlbumViewModel

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = AlbumViewModel(albumRepo, io, ui)
    }

    @Test
    fun `getAlbumDetails given a success response, verify that livedata is a success resource`() {
        //Given
        val id = "1vpkXxcn8dnyKLWhpJvtKN"
        viewModel.getAlbumDetails(id)

        val value = viewModel.album.getOrAwaitValue()
        val expected = albumRepo.getAlbumsById(id)

        assertThat(value, `is`(notNullValue()))
        assert(value.status == ResourceStatus.SUCCESS)
    }

    @Test
    fun `getAlbumDetails given a success response, verify that livedata has correct data`() {
        //Given
        val id = "1vpkXxcn8dnyKLWhpJvtKN"
        viewModel.getAlbumDetails(id)

        val value = viewModel.album.getOrAwaitValue()
        val resultAlbum = value.data?.let { TestAlbum.from(it) }
        val expectedAlbum = albumRepo.getAlbumsById(id)?.let { TestAlbum.from(it) }

        assert(resultAlbum == expectedAlbum)
    }

}