package com.arctouch.codechallenge.modules.home

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.app.ActivityOptionsCompat.makeSceneTransitionAnimation
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.data.MovieRequestManager
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.MovieDetail
import com.arctouch.codechallenge.modules.detail.MovieDetailsActivity
import com.arctouch.codechallenge.modules.home.enums.MovieSearchType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    var movieLiveData = MutableLiveData<Pair<List<Movie>, Boolean>>()
    var isLoadingLiveData = MutableLiveData<Boolean>()
    var showErrorMessageLiveDatabase = MutableLiveData<String>()

    private var currentPage: Long = 1

    private var lastSearchType = MovieSearchType.UPCOMING

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun getUpcomingMovieList(context: Context) {
        isLoadingLiveData.value = true
        if (lastSearchType != MovieSearchType.UPCOMING) currentPage = 1
        lastSearchType = MovieSearchType.UPCOMING

        compositeDisposable.add(
                MovieRequestManager.getUpcomingMoviesWithGenre(currentPage)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if (it.isNotEmpty()) movieLiveData.postValue(Pair(it, currentPage != 1L))
                            else {
                                showErrorMessageLiveDatabase.value = context.getString(R.string.desculpa_nao_foi_encontrado_mais_nenhum_filme)
                            }
                            isLoadingLiveData.value = false
                        }, {
                            movieLiveData.value = Pair(emptyList(), false)
                            isLoadingLiveData.value = false
                            showErrorMessageLiveDatabase.value = context.getString(R.string.ocorreu_algum_erro_ao_baixar_os_filmes_em_lancamentos)
                        })
        )
    }

    fun searchMovieList(name: String, context: Context) {
        isLoadingLiveData.value = true
        if (lastSearchType != MovieSearchType.SEARCH) currentPage = 1
        lastSearchType = MovieSearchType.SEARCH

        compositeDisposable.add(
                MovieRequestManager.searchMovieWithGenre(name, currentPage)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if (it.isNotEmpty()) movieLiveData.postValue(Pair(it, currentPage != 1L))
                            else showErrorMessageLiveDatabase.value = context.getString(R.string.desculpa_nao_foi_encontrado_mais_nenhum_filme)
                            isLoadingLiveData.value = false
                        }, {
                            movieLiveData.value = Pair(emptyList(), false)
                            isLoadingLiveData.value = false
                            showErrorMessageLiveDatabase.value = context.getString(R.string.ocorreu_algum_erro_ao_buscar_pelo_filme)
                        })
        )
    }

    private fun refreshList(name: String?, context: Context) {
        when (lastSearchType) {
            MovieSearchType.SEARCH -> name?.let {
                if (it.isBlank() || it.isEmpty()) getUpcomingMovieList(context) else searchMovieList(name, context)
            }
            MovieSearchType.UPCOMING -> getUpcomingMovieList(context)
        }
    }

    fun updateMovieList(name: String?, context: Context) {
        currentPage = 1
        refreshList(name, context)
    }

    fun getNextPage(name: String?, context: Context) {
        currentPage++
        refreshList(name, context)
    }

    fun goToMovieDetails(activity: Activity, viewList: List<View>?, movie: MovieDetail) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra("movie", movie)

        if (viewList != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val pairs = viewList.map { androidx.core.util.Pair(it, it.transitionName) }.toTypedArray()
            val activityOptionsCompat = makeSceneTransitionAnimation(activity, *pairs)
            activity.startActivity(intent, activityOptionsCompat.toBundle())
        } else {
            activity.startActivity(intent)
        }
    }
}