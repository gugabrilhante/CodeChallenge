package com.arctouch.codechallenge.modules.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.ServerApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.extensions.getViewModel
import com.arctouch.codechallenge.modules.home.adapter.HomeAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    var viewModel:HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = getViewModel()

        api.upcomingMovies(ServerApi.API_KEY, ServerApi.DEFAULT_LANGUAGE, 1, ServerApi.DEFAULT_REGION)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val moviesWithGenres = it.results.map { movie ->
                    movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                }
                recyclerView.adapter = HomeAdapter(moviesWithGenres)
                progressBar.visibility = View.GONE
            }
    }
}
