package com.arctouch.codechallenge.model

import com.squareup.moshi.Json

data class GenreResponse(
        @Json(name = "genres")val genres: List<Genre>
)