package com.arctouch.codechallenge.modules.detail

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.extensions.animateAlpha
import com.arctouch.codechallenge.extensions.getViewModel
import com.arctouch.codechallenge.model.Movie
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
        registeMovieInfoObservable()
        registerProccessGenreTextObservable()
    }

    private fun registerMovieObservable() {
        viewModel?.movieLiveData?.observe(this, Observer { movie: Movie? ->
            movie?.let {
                titleTextView.text = it.title
                it.posterPath?.let { poster: String ->
                    Glide.with(this)
                            .load(MovieImageUrlBuilder.buildPosterUrl(poster))
                            .into(movieImageView)
                }
            }
        })
    }

    private fun registeMovieInfoObservable() {
        viewModel?.movieInfoLiveData?.observe(this, Observer { movie: Movie? ->
            movie?.let {
                overviewTextView.text = it.overview
                overviewTextView.animateAlpha(1f, 150)
                viewModel?.processGenreText(this, it.genres)
            }
        })
    }

    private fun registerProccessGenreTextObservable() {
        viewModel?.genreTextLiveData?.observe(this, Observer {
            genreTextView.text = it
            genreTextView.animateAlpha(1f, 150)
        })
    }
}
