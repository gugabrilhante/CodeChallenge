package com.arctouch.codechallenge

import com.arctouch.codechallenge.data.MovieRequestManager
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.MoviesResponse
import com.squareup.moshi.Moshi
import io.reactivex.Single
import org.junit.Test

class MovieRequestManagerTest {

    val movieListJson = "{\"results\":[{\"vote_count\":1692,\"id\":335983,\"video\":false,\"vote_average\":6.6,\"title\":\"Venom\",\"popularity\":404.784,\"poster_path\":\"/wdcc8n9BB5gO5Y7zIhHLSzxSTc6.jpg\",\"original_language\":\"en\",\"original_title\":\"Venom\",\"genre_ids\":[878],\"backdrop_path\":\"/VuukZLgaCrho2Ar8Scl9HtV3yD.jpg\",\"adult\":false,\"overview\":\"Eddie Brock (Tom Hardy) é um jornalista investigativo, que tem um quadro próprio em uma emissora local. Um dia, ele é escalado para entrevistar Carlton Drake (Riz Ahmed), o criador da Fundação Vida, que tem investido bastante em missões espaciais de forma a encontrar possíveis usos medicinais para a humanidade. Após acessar um documento sigiloso enviado à sua namorada, a advogada Anne Weying (Michelle Williams), Brock descobre que Drake tem feito experimentos científicos em humanos. Ele resolve denunciar esta situação durante a entrevista, o que faz com que seja demitido. Seis meses depois, o ainda desempregado Brock é procurado pela dra. Dora Skirth (Jenny Slate) com uma denúncia: Drake estaria usando simbiontes alienígenas em testes com humanos, muitos deles mortos como cobaias.\",\"release_date\":\"2018-10-03\"},{\"vote_count\":351,\"id\":424139,\"video\":false,\"vote_average\":6.7,\"title\":\"Halloween\",\"popularity\":297.383,\"poster_path\":\"/1KtkRCnCoQS22VZFGm0gph3QdL4.jpg\",\"original_language\":\"en\",\"original_title\":\"Halloween\",\"genre_ids\":[27,53],\"backdrop_path\":\"/tZ358Wk4BnOc4FjdGsiexAUvCMH.jpg\",\"adult\":false,\"overview\":\"Uma equipe de documentários britânica viaja aos EUA para visitar Michael na prisão, para uma retrospectiva sobre a noite de terror, mas seu projeto entra em caos e se torna mais interessante quando Michael escapa da custódia, recupera sua antiga máscara e busca vingança de Laurie, fazendo outras vítimas em seu caminho.\",\"release_date\":\"2018-10-18\"},{\"vote_count\":246,\"id\":463272,\"video\":false,\"vote_average\":6.2,\"title\":\"Johnny English 3.0\",\"popularity\":106.293,\"poster_path\":\"/qyRVCbl9qJSVFLXps14sB7fwhC3.jpg\",\"original_language\":\"en\",\"original_title\":\"Johnny English Strikes Again\",\"genre_ids\":[12,10751,28,35],\"backdrop_path\":\"/yCOLqh5MOGyYdo58Ap0aWvKop9h.jpg\",\"adult\":false,\"overview\":\"Em sua nova aventura, Johnny English (Rowan Atkinson) é a última salvação do serviço secreto quando um ataque cibernético revela as identidades de todos os agentes do país. Tirado de sua aposentadoria, ele volta à ativa com a missão de achar o hacker por trás do ataque. Com poucas habilidades e métodos analógicos, Johnny English precisa superar os desafios do mundo tecnológico para fazer da missão um sucesso.\",\"release_date\":\"2018-09-13\"}],\"page\":1,\"total_results\":353,\"dates\":{\"maximum\":\"2018-11-24\",\"minimum\":\"2018-10-28\"},\"total_pages\":18}"

    val genreListJson = "{\"genres\":[{\"id\":28,\"name\":\"Ação\"},{\"id\":12,\"name\":\"Aventura\"},{\"id\":16,\"name\":\"Animação\"},{\"id\":35,\"name\":\"Comédia\"},{\"id\":80,\"name\":\"Crime\"},{\"id\":99,\"name\":\"Documentário\"},{\"id\":18,\"name\":\"Drama\"},{\"id\":10751,\"name\":\"Família\"},{\"id\":14,\"name\":\"Fantasia\"},{\"id\":36,\"name\":\"História\"},{\"id\":27,\"name\":\"Terror\"},{\"id\":10402,\"name\":\"Música\"},{\"id\":9648,\"name\":\"Mistério\"},{\"id\":10749,\"name\":\"Romance\"},{\"id\":878,\"name\":\"Ficção científica\"},{\"id\":10770,\"name\":\"Cinema TV\"},{\"id\":53,\"name\":\"Thriller\"},{\"id\":10752,\"name\":\"Guerra\"},{\"id\":37,\"name\":\"Faroeste\"}]}"

    private fun mockMovieResponse(): MoviesResponse {
        val moshi = Moshi.Builder().build().adapter(MoviesResponse::class.java)
        return moshi.fromJson(movieListJson)
    }

    private fun mockGenreResponse(): GenreResponse {
        val moshi = Moshi.Builder().build().adapter(GenreResponse::class.java)
        return moshi.fromJson(genreListJson)
    }

    @Test
    fun zipMovieAndGenreResponse() {

        val movieResponseObservable = Single.just(mockMovieResponse())
        val genreResponseObservable = Single.just(mockGenreResponse())

        MovieRequestManager.zipSingleMovieAndGenre(movieResponseObservable, genreResponseObservable)
                .test()
                .assertOf { it.assertComplete() }
    }

    @Test
    fun testZipResult() {

        val mockMovieResponse = mockMovieResponse()
        val mockGenreResponse = mockGenreResponse()

        val movieResponseSingle = Single.just(mockMovieResponse())
        val genreResponseSingle = Single.just(mockGenreResponse())

        val zipResult = mockMovieResponse.results.map { movie ->
            movie.copy(genres = mockGenreResponse.genres.filter { movie.genreIds?.contains(it.id) == true })
        }

        MovieRequestManager.zipSingleMovieAndGenre(movieResponseSingle, genreResponseSingle)
                .test()
                .assertResult(zipResult)
                .assertValueCount(1)
    }

    @Test
    fun testZipEmptyMovieList() {
        val moviesResponse = MoviesResponse(1, emptyList<Movie>(), 1, 1)

        val movieResponseSingle = Single.just(moviesResponse)
        val genreResponseSingle = Single.just(mockGenreResponse())

        MovieRequestManager.zipSingleMovieAndGenre(movieResponseSingle, genreResponseSingle)
                .test()
                .assertResult(emptyList<Movie>())
    }
}