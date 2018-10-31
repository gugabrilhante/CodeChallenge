package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

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

    @GET("search/movie/")
    fun searchMovies(
            @Query("api_key") api_key: String,
            @Query("language") language: String,
            @Query("query") name: String,
            @Query("page") page: Long
    ): Single<MoviesResponse>}
