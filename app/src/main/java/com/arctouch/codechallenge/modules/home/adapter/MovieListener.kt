package com.arctouch.codechallenge.modules.home.adapter

import android.view.View
import com.arctouch.codechallenge.model.MovieDetail

interface MovieListener {
    fun onMovieClick(movie: MovieDetail, viewList: List<View>)
}