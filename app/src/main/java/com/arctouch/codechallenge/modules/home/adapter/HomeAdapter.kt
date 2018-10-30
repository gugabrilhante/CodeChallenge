package com.arctouch.codechallenge.modules.home.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.arctouch.codechallenge.model.Movie

class HomeAdapter(val listener: MovieListener) : RecyclerView.Adapter<HomeViewHolder>() {

    var movieList: List<Movie> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder = HomeViewHolder(parent, listener)

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) = holder.bind(movieList[position])
}
