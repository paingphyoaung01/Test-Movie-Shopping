package com.example.movieshoppingtesting.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.movieshoppingtesting.R
import com.example.movieshoppingtesting.util.Status
import com.example.movieshoppingtesting.viewmodel.MovieShoppingViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_image.view.*
import kotlinx.android.synthetic.main.fragment_add_shopping_movie_item.*
import javax.inject.Inject

@AndroidEntryPoint
class AddShoppingMovieItemFragment @Inject constructor(
    val glide: RequestManager
): Fragment(R.layout.fragment_add_shopping_movie_item) {

    lateinit var viewModel : MovieShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MovieShoppingViewModel::class.java)
        subscribeToObservers()

        btnAddShoppingItem.setOnClickListener {
            viewModel.checkUserInsertData(
                etShoppingItemName.text.toString(),
                etShoppingItemAmount.text.toString(),
                etShoppingItemPrice.text.toString()
            )
        }

        ivShoppingImage.setOnClickListener {
            findNavController().navigate(
                AddShoppingMovieItemFragmentDirections.actionAddShoppingMovieItemFragmentToMoviePickFragment()
            )
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setCurImageUrl("")
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun subscribeToObservers() {
        viewModel.curImageUrl.observe(viewLifecycleOwner, Observer {


            if(it != "") {
                glide.load("https://image.tmdb.org/t/p/w500$it").into(ivShoppingImage)
//                Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500$it")
//                    .into(ivShoppingImage)
            }
        })
        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { result->
                when(result.status) {
                    Status.SUCCESS -> {
                        Snackbar.make(
                            requireActivity().activity_main_layout,
                            "Added Shopping Item",
                            Snackbar.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            requireActivity().activity_main_layout,
                            result.message ?: "An Unknown Error Occurred",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    Status.LOADING -> {
                        /* Something */
                    }
                }
            }
        })
    }
}