package com.arctouch.codechallenge.modules.detail

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.ServerInteractor
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.DisposableManager
import com.arctouch.codechallenge.util.StringUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel(app: Application) : AndroidViewModel(app) {

    var movieLiveData = MutableLiveData<Movie>()

    var movieInfoLiveData = MutableLiveData<Movie>()

    var genreTextLiveData = MutableLiveData<String>()

    fun getMovieFromIntent(intent: Intent) {
//        intent.getParcelableExtra<Movie>("movie")?.let { movie: Movie ->
//            movieLiveData.value = movie
//            movie.id?.let {
//                getMovieInfo(it)
//            }
//        }
    }

    private fun getMovieInfo(movieId: Long) {
        DisposableManager.add(
                ServerInteractor().getMovieInfo(movieId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            movieInfoLiveData.postValue(it)
                        }, {

                        })
        )
    }

    fun processGenreText(context: Context, genres: List<Genre?>?) {
        var genreText = ""
        genres?.let { list: List<Genre?> ->
            genreText = StringUtils.getWordsToPhrase(
                    context.getString(R.string.and),
                    list.map { it?.name }
            )
        }
        genreTextLiveData.value = genreText
    }
}