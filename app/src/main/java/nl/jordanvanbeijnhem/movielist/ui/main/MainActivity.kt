package nl.jordanvanbeijnhem.movielist.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import nl.jordanvanbeijnhem.movielist.R
import nl.jordanvanbeijnhem.movielist.adapter.MovieAdapter
import nl.jordanvanbeijnhem.movielist.model.Movie
import nl.jordanvanbeijnhem.movielist.ui.movie.MovieActivity
import kotlin.math.floor

class MainActivity : AppCompatActivity() {

    private val movies = arrayListOf<Movie>()
    private val movieAdapter = MovieAdapter(movies) { movie -> onMovieClick(movie) }
    private lateinit var viewmodel: MainActivityViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initViewmodel()
    }

    private fun initViews() {
        val gridLayoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
        rvMovies.layoutManager = gridLayoutManager
        rvMovies.viewTreeObserver.addOnGlobalLayoutListener(object :
            OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                rvMovies.viewTreeObserver.removeOnGlobalLayoutListener(this)
                gridLayoutManager.spanCount = calculateSpanCount()
                gridLayoutManager.requestLayout()
            }
        })
        rvMovies.adapter = movieAdapter

        btnSubmit.setOnClickListener { viewmodel.getMoviesForYear(etYear.text.toString()) }
    }

    private fun initViewmodel() {
        viewmodel = ViewModelProviders.of(this).get(MainActivityViewmodel::class.java)
        viewmodel.movies.observe(this, Observer {
            movies.clear()
            movies.addAll(it)
            movieAdapter.notifyDataSetChanged()
        })

        viewmodel.loading.observe(this, Observer {
            pbLoading.isVisible = it
        })

        viewmodel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun onMovieClick(movie: Movie) {
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra("MOVIE", movie)
        startActivity(intent)
    }

    private fun calculateSpanCount(): Int {
        val viewWidth = rvMovies.measuredWidth
        val cardViewWidth = resources.getDimension(R.dimen.poster_width)
        val cardViewMargin = resources.getDimension(R.dimen.margin)
        val spanCount = floor((viewWidth / (cardViewWidth + cardViewMargin)).toDouble()).toInt()
        return if (spanCount >= 1) spanCount else 1
    }
}
