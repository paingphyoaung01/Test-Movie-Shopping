package com.example.movieshoppingtesting.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.movieshoppingtesting.adapter.ImageAdapter
import com.example.movieshoppingtesting.adapter.ShoppingItemAdapter
import com.example.movieshoppingtesting.repository.FakeSearchMovieRepositoryAndroidTest
import com.example.movieshoppingtesting.viewmodel.MovieShoppingViewModel
import org.junit.Assert.*
import javax.inject.Inject

class MovieShoppingFragmentFactoryTest @Inject constructor(
    private val imageAdapter : ImageAdapter,
    private val glide : RequestManager,
    private val shoppingItemAdapter: ShoppingItemAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            MoviePickFragment::class.java.name -> MoviePickFragment(imageAdapter)
            AddShoppingMovieItemFragment::class.java.name -> AddShoppingMovieItemFragment(glide)
            ShoppingFragment::class.java.name -> ShoppingFragment(
                shoppingItemAdapter,
                MovieShoppingViewModel(FakeSearchMovieRepositoryAndroidTest())
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}