package com.arctouch.codechallenge.model

import com.squareup.moshi.Json

data class Genre(
        @Json(name = "id")val id: Int,
        @Json(name = "name")val name: String)
