package com.arctouch.codechallenge

import com.arctouch.codechallenge.data.GenreCache
import com.arctouch.codechallenge.data.GenreRequestManager
import com.arctouch.codechallenge.model.GenreResponse
import com.squareup.moshi.Moshi
import org.junit.Test

class GenreRequestManagerTest {

    val genreListJson = "{\"genres\":[{\"id\":28,\"name\":\"Ação\"},{\"id\":12,\"name\":\"Aventura\"},{\"id\":16,\"name\":\"Animação\"},{\"id\":35,\"name\":\"Comédia\"},{\"id\":80,\"name\":\"Crime\"},{\"id\":99,\"name\":\"Documentário\"},{\"id\":18,\"name\":\"Drama\"},{\"id\":10751,\"name\":\"Família\"},{\"id\":14,\"name\":\"Fantasia\"},{\"id\":36,\"name\":\"História\"},{\"id\":27,\"name\":\"Terror\"},{\"id\":10402,\"name\":\"Música\"},{\"id\":9648,\"name\":\"Mistério\"},{\"id\":10749,\"name\":\"Romance\"},{\"id\":878,\"name\":\"Ficção científica\"},{\"id\":10770,\"name\":\"Cinema TV\"},{\"id\":53,\"name\":\"Thriller\"},{\"id\":10752,\"name\":\"Guerra\"},{\"id\":37,\"name\":\"Faroeste\"}]}"

    private fun mockGenreResponse(): GenreResponse {
        val moshi = Moshi.Builder().build().adapter(GenreResponse::class.java)
        return moshi.fromJson(genreListJson)
    }

    @Test
    fun getGenreSingleTest(){
        GenreCache.cacheGenres(mockGenreResponse().genres)

        GenreRequestManager.getGenreListSingle()
                .test()
                .assertResult(GenreResponse(GenreCache.genres))
    }

}