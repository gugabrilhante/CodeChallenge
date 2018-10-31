package com.arctouch.codechallenge.util

import com.arctouch.codechallenge.api.ServerInteractor
import com.arctouch.codechallenge.constants.URLS_PATHS

object MovieImageUrlBuilder {

    fun buildPosterUrl(posterPath: String): String {
        return "${URLS_PATHS.POSTER_URL}$posterPath?api_key=${ServerInteractor.API_KEY}"
    }
}
