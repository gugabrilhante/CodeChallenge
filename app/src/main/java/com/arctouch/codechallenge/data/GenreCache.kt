package com.arctouch.codechallenge.data

import com.arctouch.codechallenge.model.Genre

object GenreCache {

    var genres = listOf<Genre>()

    fun cacheGenres(genres: List<Genre>) {
        this.genres = genres
    }
}
