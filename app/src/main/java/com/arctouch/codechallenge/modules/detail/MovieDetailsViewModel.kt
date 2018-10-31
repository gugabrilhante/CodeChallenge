package com.arctouch.codechallenge.modules.detail

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.model.MovieDetail

class MovieDetailsViewModel(app: Application) : AndroidViewModel(app) {

    var movieLiveData = MutableLiveData<MovieDetail>()

    fun getMovieFromIntent(intent: Intent) {
        intent.getParcelableExtra<MovieDetail>("movie")?.let { movie: MovieDetail ->
            movieLiveData.value = movie
        }
    }

}