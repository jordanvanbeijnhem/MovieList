package nl.jordanvanbeijnhem.movielist.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import nl.jordanvanbeijnhem.movielist.model.ApiResponse
import nl.jordanvanbeijnhem.movielist.model.Movie
import nl.jordanvanbeijnhem.movielist.repository.MoviesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewmodel(application: Application) : AndroidViewModel(application) {

    private val numbersRepository = MoviesRepository()
    val movies = MutableLiveData<List<Movie>>()
    val loading = MutableLiveData<Boolean>(false)
    val error = MutableLiveData<String>()

    fun getMoviesForYear(year: String) {
        loading.value = true
        numbersRepository.getMoviesForYear(year).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) movies.value = response.body()?.movies
                else error.value = "An error occurred: ${response.errorBody().toString()}"
                loading.value = false
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                error.value = t.message
                loading.value = false
            }
        })
    }
}