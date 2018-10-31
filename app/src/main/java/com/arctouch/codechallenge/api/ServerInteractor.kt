package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.constants.URLS_PATHS
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.MoviesResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class ServerInteractor {

    private val serverApi: ServerApi

    companion object {
        const val API_KEY = "1f54bd990f1cdfb230adb312546d765d"
        const val DEFAULT_LANGUAGE = "pt-BR"
        const val DEFAULT_REGION = "BR"
    }

    init {
        val clientBuilder = OkHttpClient.Builder()

        val retrofit = Retrofit.Builder()
                .baseUrl(URLS_PATHS.BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        serverApi = retrofit
                .create(ServerApi::class.java)
    }

    fun getUpcomingMovies(page: Long): Single<MoviesResponse> {
        return this.serverApi.upcomingMovies(API_KEY, DEFAULT_LANGUAGE, page, DEFAULT_REGION)
    }

    fun searchMovie(name: String, page: Long): Single<MoviesResponse> {
        return this.serverApi.searchMovies(API_KEY, DEFAULT_LANGUAGE, name, page)
    }

    fun getGenreList(): Single<GenreResponse> {
        return this.serverApi.genres(API_KEY, DEFAULT_LANGUAGE)
    }

}