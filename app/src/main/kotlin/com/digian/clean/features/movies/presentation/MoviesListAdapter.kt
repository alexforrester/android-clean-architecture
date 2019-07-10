package com.digian.clean.features.movies.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digian.clean.R
import com.digian.clean.features.movies.domain.entities.MovieEntity


/**
 * Created by Alex Forrester on 17/04/2019.
 */
internal class MoviesListAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {

    internal var data: List<MovieEntity>? = null

    class MovieViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {
        fun bind(movieEntity: MovieEntity, onItemClickListener: OnItemClickListener) {
            textView.setOnClickListener {
                onItemClickListener.onItemClick(movieEntity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MovieViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movies_list_item, parent, false) as TextView

        return MovieViewHolder(
            textView
        )
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
    fun onItemClick(movieEntity: MovieEntity)
}