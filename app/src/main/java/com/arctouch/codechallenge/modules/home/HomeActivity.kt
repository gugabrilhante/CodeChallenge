package com.arctouch.codechallenge.modules.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.extensions.buildAlertDialog
import com.arctouch.codechallenge.extensions.getViewModel
import com.arctouch.codechallenge.extensions.setBackgroundAnimated
import com.arctouch.codechallenge.extensions.verticalLinearLayout
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.modules.home.adapter.HomeAdapter
import com.arctouch.codechallenge.modules.home.adapter.MovieListener
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), MovieListener {

    var viewModel: HomeViewModel? = null

    private var adapter = HomeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = getViewModel()
        setupViews()
        registerObservables()
        viewModel?.getUpcomingMovieList()
        savedInstanceState?.let { savedInstance: Bundle ->
            recyclerView.layoutManager?.onRestoreInstanceState(savedInstance.getParcelable("LayoutManagerInstance"))
        }
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        recyclerView.layoutManager?.let {
            savedInstanceState.putParcelable(
                    "LayoutManagerInstance",
                    it.onSaveInstanceState()
            )
        }
        super.onSaveInstanceState(savedInstanceState)
    }

    private fun setupViews() {
        recyclerView.adapter = adapter
        recyclerView.verticalLinearLayout(this)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel?.searchMovieList(query)
                return false
            }

        })
        searchView.setOnSearchClickListener {
            toolbar.setBackgroundAnimated(250, R.color.colorPrimary, R.color.white)
        }
        searchView.setOnCloseListener {
            toolbar.setBackgroundAnimated(250, R.color.white, R.color.colorPrimary)
            false
        }
        swipeRefreshLayout.setOnRefreshListener {
            viewModel?.updateMovieList(searchView.query?.toString())
            swipeRefreshLayout.isRefreshing = true
        }
    }

    private fun registerObservables() {
        registerListMoviesObservable()
        registerIsLoadingObservable()
        registerShowErrorMessageObservable()
    }

    private fun registerListMoviesObservable() {
        viewModel?.movieLiveData?.observe(this, Observer { movieList: List<Movie>? ->
            movieList?.let {
                adapter.movieList = it
            }
        })
    }

    private fun registerIsLoadingObservable() {
        viewModel?.isLoadingLiveData?.observe(this, Observer { isLoading: Boolean? ->
            isLoading?.let {
                swipeRefreshLayout.isRefreshing = it
            }
        })
    }

    private fun registerShowErrorMessageObservable() {
        viewModel?.showErrorMessageLiveDatabase?.observe(this, Observer {
            buildAlertDialog(this, "Error", it).show()
        })
    }

    override fun onMovieClick(movie: Movie, viewList: List<View>) {
        viewModel?.goToMovieDetails(this, viewList, movie)
    }
}
