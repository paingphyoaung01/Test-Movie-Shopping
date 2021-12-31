package com.example.movieshoppingtesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.movieshoppingtesting.R
import com.example.movieshoppingtesting.models.entity.SearchMovieItem
import kotlinx.android.synthetic.main.custom_image.view.*
import kotlinx.android.synthetic.main.custom_shopping.view.*
import javax.inject.Inject

class ShoppingItemAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingItemViewHolder>() {

    inner class ShoppingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<SearchMovieItem>() {
        override fun areItemsTheSame(oldItem: SearchMovieItem, newItem: SearchMovieItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SearchMovieItem, newItem: SearchMovieItem): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var shoppingItem: List<SearchMovieItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        return ShoppingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_shopping,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val shoppingItem = shoppingItem[position]
        holder.itemView.apply {
            glide.load("https://image.tmdb.org/t/p/w500${shoppingItem.poster_path}").into(this.ShoppingImage)

//            Glide.with(this.context).load("https://image.tmdb.org/t/p/w500${shoppingItem.poster_path}")
//                .into(this.ShoppingImage)

            tvName.text = shoppingItem.title
            tvShoppingItemAmount.text = shoppingItem.amount.toString()
            tvShoppingItemPrice.text = shoppingItem.price.toString()
        }
    }

    override fun getItemCount(): Int {
        return shoppingItem.size
    }


}