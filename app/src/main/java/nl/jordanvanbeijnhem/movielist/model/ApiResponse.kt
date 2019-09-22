package nl.jordanvanbeijnhem.movielist.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(@SerializedName("results") var movies: List<Movie>)