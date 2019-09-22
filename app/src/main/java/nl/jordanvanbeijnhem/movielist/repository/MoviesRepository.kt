package nl.jordanvanbeijnhem.movielist.repository

import nl.jordanvanbeijnhem.movielist.api.MoviesApi
import nl.jordanvanbeijnhem.movielist.api.MoviesApiService

class MoviesRepository {

    private val moviesApi: MoviesApiService = MoviesApi.createApi()

    fun getMoviesForYear(year: String) = moviesApi.getMoviesForYear(year)
}