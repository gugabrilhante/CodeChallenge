package com.arctouch.codechallenge.modules.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.extensions.animateAlpha
import com.arctouch.codechallenge.extensions.getViewModel
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    private var viewModel: MovieDetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        viewModel = getViewModel()
        registerObservables()
        viewModel?.getMovieFromIntent(intent)
    }

    private fun registerObservables() {
        registerMovieObservable()
    }

    private fun registerMovieObservable() {
        viewModel?.movieLiveData?.observe(this, Observer {
            titleTextView.text = it.title
            genreTextView.text = it.genreText
            overviewTextView.text = it.overview
            it.posterPath?.let { poster: String ->
                Glide.with(this)
                        .load(MovieImageUrlBuilder.buildPosterUrl(poster))
                        .into(movieImageView)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        showOverViewA()
    }

    private fun showOverViewA(){
        overviewTextView.animateAlpha(1f, 250)
    }
}
