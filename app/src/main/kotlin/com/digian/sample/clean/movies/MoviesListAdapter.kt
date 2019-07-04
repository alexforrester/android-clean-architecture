package com.digian.sample.clean.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digian.sample.clean.movies.data.model.MovieData
import com.digian.sample.clean.R


/**
 * Created by Alex Forrester on 17/04/2019.
 */
internal class MoviesListAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {

    internal var data: List<MovieData>? = null

    class MovieViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {
        fun bind(movieData: MovieData, onItemClickListener: OnItemClickListener) {
            textView.setOnClickListener {
                onItemClickListener.onItemClick(movieData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MovieViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movies_list_item, parent, false) as TextView

        return MovieViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        data?.let {
            holder.bind(it[position], onItemClickListener)
        }

        holder.textView.text = data?.get(position)?.title
    }

    override fun getItemCount() = data?.size ?: 0

}

internal interface OnItemClickListener {
    fun onItemClick(movieData : MovieData)
}