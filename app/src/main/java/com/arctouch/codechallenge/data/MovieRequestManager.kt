package com.arctouch.codechallenge.data

import com.arctouch.codechallenge.api.ServerInteractor
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.MoviesResponse
import io.reactivex.Single
import io.reactivex.functions.BiFunction

object MovieRequestManager {

    fun getUpcomingMoviesWithGenre(page:Long): Single<List<Movie>> {
        return zipSingleMovieAndGenre(
                getUpcomingMovies(page),
                GenreRequestManager.getGenreSingle()
        )
    }

    fun searchMovieWithGenre(name:String, page:Long): Single<List<Movie>> {
        return zipSingleMovieAndGenre(
                searchMovieByName(name, page),
                GenreRequestManager.getGenreSingle()
        )
    }

    private fun getUpcomingMovies(page:Long) = ServerInteractor().getUpcomingMovies(page)

    private fun searchMovieByName(name:String, page:Long) = ServerInteractor().searchMovie(name, page)

    fun zipSingleMovieAndGenre(movieResponseSingle: Single<MoviesResponse>, GenreResponseSingle: Single<GenreResponse>): Single<List<Movie>> {
        return Single.zip(movieResponseSingle, GenreResponseSingle, BiFunction<MoviesResponse, GenreResponse, List<Movie>> { movieResponse, genreResponse ->
            Cache.cacheGenres(genreResponse.genres)
            return@BiFunction movieResponse.results.map { movie ->
                movie.copy(genres = genreResponse.genres.filter { movie.genreIds?.contains(it.id) == true })
            }
        })
    }

}