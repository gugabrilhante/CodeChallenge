package com.arctouch.codechallenge.data

import com.arctouch.codechallenge.api.ServerInteractor
import com.arctouch.codechallenge.model.GenreResponse
import io.reactivex.Single

object GenreRequestManager {

    fun getGenreSingle(): Single<GenreResponse> {
        val cache = Cache.genres
        if (cache.isEmpty()) {
            return ServerInteractor().getGenreList()
        }
        val genreResponse = GenreResponse(cache)
        return Single.just(genreResponse)
    }

}