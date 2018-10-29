package com.arctouch.codechallenge.modules.home.adapter

import android.view.View
import com.arctouch.codechallenge.model.Movie

interface MovieListener {
    fun onMovieClick(movie: Movie, viewList: List<View>)
}