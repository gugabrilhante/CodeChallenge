package com.arctouch.codechallenge

import com.arctouch.codechallenge.api.ServerInteractor
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieImageUrlBuilderTest {

    @Test
    fun urlBuilderTest(){
        val text = "anyTest"
        val result = MovieImageUrlBuilder.buildPosterUrl(text)

        assertTrue(result.contains(Regex("[^ ]/")))
        assertTrue(result.contains("$text?api_key=${ServerInteractor.API_KEY}"))
    }

}