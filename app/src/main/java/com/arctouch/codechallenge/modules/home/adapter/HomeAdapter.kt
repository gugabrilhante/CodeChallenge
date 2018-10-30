package com.arctouch.codechallenge.modules.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.model.Movie

class HomeAdapter(val listener: MovieListener) : RecyclerView.Adapter<HomeViewHolder>() {

    var movieList: MutableList<Movie> = arrayListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun addToList(list:List<Movie>){
        movieList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder = HomeViewHolder(parent, listener)

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) = holder.bind(movieList[position])
}
