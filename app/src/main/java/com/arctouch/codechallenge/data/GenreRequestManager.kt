package com.arctouch.codechallenge.data

import com.arctouch.codechallenge.api.ServerInteractor
import com.arctouch.codechallenge.model.GenreResponse
import io.reactivex.Single

object GenreRequestManager {

    fun getGenreListSingle(): Single<GenreResponse> {
        val cache = GenreCache.genres
        if (cache.isEmpty()) {
            return ServerInteractor().getGenreList()
        }
        val genreResponse = GenreResponse(cache)
        return Single.just(genreResponse)
    }

}