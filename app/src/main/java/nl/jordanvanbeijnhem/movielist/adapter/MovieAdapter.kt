package nl.jordanvanbeijnhem.movielist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*
import nl.jordanvanbeijnhem.movielist.R
import nl.jordanvanbeijnhem.movielist.model.Movie

class MovieAdapter(private val movies: List<Movie>, private val onMovieClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { onMovieClick(movies[adapterPosition]) }
        }

        fun bind(movie: Movie) {
            itemView.tvNumber.text = (adapterPosition + 1).toString()
            Glide.with(context).load(movie.getPosterUrl()).into(itemView.ivPoster)
        }
    }
}