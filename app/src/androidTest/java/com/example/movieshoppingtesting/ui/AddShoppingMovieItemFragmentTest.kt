package com.example.movieshoppingtesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.movieshoppingtesting.R
import com.example.movieshoppingtesting.getOrAwaitValue
import com.example.movieshoppingtesting.launchFragmentInHiltContainer
import com.example.movieshoppingtesting.models.entity.SearchMovieItem
import com.example.movieshoppingtesting.repository.FakeSearchMovieRepositoryAndroidTest
import com.example.movieshoppingtesting.viewmodel.MovieShoppingViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingMovieItemFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()   // Test for live data

    @Inject
    lateinit var fragmentFactory: MovieShoppingFragmentFactoryTest

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun clickInsertIntoDb_shoppingItemInsertedIntoDb() {
        val testViewModel = MovieShoppingViewModel(FakeSearchMovieRepositoryAndroidTest())
        launchFragmentInHiltContainer<AddShoppingMovieItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            viewModel = testViewModel
        }

        onView(withId(R.id.etShoppingItemName))
            .perform(ViewActions.replaceText("shopping item"))
        onView(withId(R.id.etShoppingItemAmount)).perform(ViewActions.replaceText("9"))
        onView(withId(R.id.etShoppingItemPrice)).perform(ViewActions.replaceText("9.5"))
        onView(withId(R.id.btnAddShoppingItem)).perform(ViewActions.click())

//        assertThat(testViewModel.movieItems.getOrAwaitValue())
//            .contains(SearchMovieItem("shopping item", "", 9.5f, 9))
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingMovieItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.pressBack()

        Mockito.verify(navController).popBackStack()
    }
}