package com.arctouch.codechallenge.modules.home.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.extensions.inflate
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_movie.view.*

class HomeViewHolder(val parent: ViewGroup, val listener: MovieListener) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.item_movie, false))  {

    fun bind(movie: Movie) {
        itemView.titleTextView.text = movie.title
        itemView.genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
        itemView.releaseDateTextView.text = movie.releaseDate

        Glide.with(itemView)
                .load(movie.posterPath?.let { MovieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.posterImageView)
    }
}