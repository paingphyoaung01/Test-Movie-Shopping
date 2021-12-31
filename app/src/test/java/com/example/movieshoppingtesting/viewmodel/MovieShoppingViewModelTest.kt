package com.example.movieshoppingtesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieshoppingtesting.MainCoroutineRule
import com.example.movieshoppingtesting.getOrAwaitValueTest
import com.example.movieshoppingtesting.repository.FakeSearchMovieRepository
import com.example.movieshoppingtesting.util.Constants.Companion.MAX_NAME_LENGTH
import com.example.movieshoppingtesting.util.Constants.Companion.MAX_PRICE_LENGTH
import com.example.movieshoppingtesting.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieShoppingViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MovieShoppingViewModel

    @Before
    fun setup() {
        viewModel = MovieShoppingViewModel(FakeSearchMovieRepository())
    }

    @Test
    fun `user insert data with empty field, return error`() {
        viewModel.checkUserInsertData("name", "", "100.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `user insert data with too long name, return error`() {

        val string = buildString {
            for (i in 1..MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }

        viewModel.checkUserInsertData(string, "2", "100.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `user insert data with too long price, return error`() {

        val string = buildString {
            for (i in 1..MAX_PRICE_LENGTH + 1) {
                append(i)
            }
        }

        viewModel.checkUserInsertData("name", "2", string)

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `user insert data with too high amount, return error`() {


        viewModel.checkUserInsertData("name", "99999999999999999999999999999", "5.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `user insert data with valid input, return success`() {

        viewModel.checkUserInsertData("name", "5", "5.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

}