package nl.jordanvanbeijnhem.movielist.api

import nl.jordanvanbeijnhem.movielist.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET("discover/movie?api_key=85c2320de67aa7034c565642198263a7&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&")
    fun getMoviesForYear(@Query("primary_release_year") year: String): Call<ApiResponse>
}