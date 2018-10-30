package com.arctouch.codechallenge.model

import com.squareup.moshi.Json

data class Movie(
        @Json(name = "id") val id: Long,
        @Json(name = "title") val title: String,
        @Json(name = "overview") val overview: String?,
        @Json(name = "genres") val genres: List<Genre>?,
        @Json(name = "genre_ids") val genreIds: List<Int>?,
        @Json(name = "poster_path") val posterPath: String?,
        @Json(name = "backdrop_path") val backdropPath: String?,
        @Json(name = "release_date") val releaseDate: String?
)