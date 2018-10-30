package com.arctouch.codechallenge.modules.home

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.app.ActivityOptionsCompat.makeSceneTransitionAnimation
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.api.ServerInteractor
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.MoviesResponse
import com.arctouch.codechallenge.modules.detail.MovieDetailsActivity
import com.arctouch.codechallenge.modules.home.enums.MovieSearchType
import com.arctouch.codechallenge.util.DisposableManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    var movieLiveData = MutableLiveData<List<Movie>>()
    var isLoadingLiveData = MutableLiveData<Boolean>()
    var showErrorMessageLiveDatabase = MutableLiveData<String>()

    private var lastSearchType = MovieSearchType.UPCOMING


    fun getUpcomingMovieList() {
        isLoadingLiveData.value = true
        lastSearchType = MovieSearchType.UPCOMING
        DisposableManager.add(
                ServerInteractor().getUpcomingMovies(1)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ response: MoviesResponse ->
                            response.results?.let {
                                movieLiveData.postValue(it.filterNotNull())
                                isLoadingLiveData.value = false
                            }

                        }, {
                            movieLiveData.value = emptyList()
                            isLoadingLiveData.value = false
                            showErrorMessageLiveDatabase.value = it.message
                        })
        )
    }

    fun searchMovieList(name: String) {
        isLoadingLiveData.value = true
        lastSearchType = MovieSearchType.SEARCH
        DisposableManager.add(
                ServerInteractor().searchMovie(name, 1)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ response: MoviesResponse ->

                            response.results?.let {
                                movieLiveData.postValue(it.filterNotNull())
                                isLoadingLiveData.value = false
                            }

                        }, {
                            movieLiveData.value = emptyList()
                            isLoadingLiveData.value = false
                            showErrorMessageLiveDatabase.value = it.message
                        })
        )
    }

    fun updateMovieList(name: String?) {
        when (lastSearchType) {
            MovieSearchType.SEARCH -> name?.let {
                if (it.isBlank() || it.isEmpty()) getUpcomingMovieList() else searchMovieList(name)
            }
            MovieSearchType.UPCOMING -> getUpcomingMovieList()
        }
    }

    fun goToMovieDetails(activity: Activity, viewList: List<View>?, movie: Movie) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
//        intent.putExtra("movie", movie)

        if (viewList != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val pairs = viewList.map { androidx.core.util.Pair(it, it.transitionName) }.toTypedArray()
            val activityOptionsCompat = makeSceneTransitionAnimation(activity, *pairs)
            activity.startActivity(intent, activityOptionsCompat.toBundle())
        }else{
            activity.startActivity(intent)
        }
    }

}