package com.yb.spotifyalbums.features.album

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.yb.spotifyalbums.R
import com.yb.spotifyalbums.TestApp
import com.yb.spotifyalbums.data.repos.FakeAlbumRepo
import com.yb.spotifyalbums.domain.models.Album
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class AlbumActivityTest {

    private lateinit var scenario: ActivityScenario<AlbumActivity>
    private val id = "6SC0Omssa5QQtX22zlZGEG"
    private val album: Album? = FakeAlbumRepo().getAlbumsById(id)
    private val artists = album?.artists ?: emptyList()

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val intent = AlbumActivity.getIntent(context = ApplicationProvider.getApplicationContext(), albumId = id)
        scenario = launchActivity(intent)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun onResume_checkActionBarTitleIsCorrect() {
        val context = ApplicationProvider.getApplicationContext<TestApp>()
        onView(withText(context.getString(R.string.album_details_actionbar_title))).check(matches(isDisplayed()))
    }

    @Test
    fun onResume_checkIfAllUiItemsAreDisplayed() {
        onView(withId(R.id.album_container)).check(matches(isDisplayed()))
        onView(withId(R.id.album_cover)).check(matches(isDisplayed()))
        onView(withId(R.id.album_name)).check(matches(isDisplayed()))
        onView(withId(R.id.release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.album_label)).check(matches(isDisplayed()))
        onView(withId(R.id.track_count)).check(matches(isDisplayed()))
        onView(withId(R.id.artists_label)).check(matches(isDisplayed()))
        onView(withId(R.id.artist_list)).check(matches(isDisplayed()))
    }

    @Test
    fun onResume_checkIfAllTextViewsHaveCorrectData() {
        onView(withId(R.id.album_name)).check(matches(withText(album?.name)))
        onView(withId(R.id.release_date)).check(matches(withText(album?.releaseDate)))
        onView(withId(R.id.album_label)).check(matches(withText(album?.label)))
        onView(withId(R.id.track_count)).check(matches(withText(album?.totalTracks.toString())))
        artists.forEach { onView(withText(it.name)).check(matches(isDisplayed())) }
    }

}