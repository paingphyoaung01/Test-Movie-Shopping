package com.example.movieshoppingtesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movieshoppingtesting.ui.MovieShoppingFragmentFactory
import com.example.movieshoppingtesting.viewmodel.MovieShoppingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: MovieShoppingFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)


    }
}