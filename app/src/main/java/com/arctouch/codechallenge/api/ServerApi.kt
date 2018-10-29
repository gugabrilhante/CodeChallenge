package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerApi {

    companion object {
        const val URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "1f54bd990f1cdfb230adb312546d765d"
        const val DEFAULT_LANGUAGE = "pt-BR"
        const val DEFAULT_REGION = "BR"
    }

    @GET("genre/movie/list")
    fun genres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<GenreResponse>

    @GET("movie/upcoming")
    fun upcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Long,
        @Query("region") region: String
    ): Single<MoviesResponse>

    @GET("movie/{id}")
    fun movieInfo(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<Movie>


    @GET("search/movie/")
    fun searchMovies(
            @Query("api_key") api_key: String,
            @Query("language") language: String,
            @Query("query") name: String,
            @Query("page") page: Long
    ): Single<MoviesResponse>}
