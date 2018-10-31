package com.arctouch.codechallenge.model

import com.squareup.moshi.Json

data class MoviesResponse(
        @Json(name = "page")val page: Int,
        @Json(name = "results")val results: List<Movie>,
        @Json(name = "total_pages") val totalPages: Int,
        @Json(name = "total_results") val totalResults: Int
)