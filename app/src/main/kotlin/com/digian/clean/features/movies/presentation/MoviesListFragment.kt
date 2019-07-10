package com.digian.clean.features.movies.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digian.clean.R
import com.digian.clean.features.movies.domain.entities.MovieEntity
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * Created by Alex Forrester on 24/04/2019.
 */
class MoviesListFragment : Fragment() {

    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var moviesListAdapter: MoviesListAdapter
    private lateinit var moviesViewManager: RecyclerView.LayoutManager
    private lateinit var moviesListViewModel: MoviesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        moviesListViewModel = ViewModelProviders.of(this).get(MoviesListViewModel::class.java)
        moviesViewManager = LinearLayoutManager(this.context)
        moviesListAdapter = MoviesListAdapter(object :
            OnItemClickListener {
            override fun onItemClick(movieEntity: MovieEntity) {
                val action =
                    MoviesListFragmentDirections.actionMoviesFragmentToMovieDetailFragment(
                        movieEntity.id
                    )
                findNavController().navigate(action)
            }
        })

        moviesRecyclerView = movies_recycler_view.apply {
            setHasFixedSize(true)

            layoutManager = moviesViewManager
            adapter = moviesListAdapter

            addItemDecoration(DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation))
        }

        moviesListViewModel.movies.observe(this,
            Observer<List<MovieEntity>> { popularMovies ->
                moviesListAdapter.data = popularMovies
            })

        moviesListViewModel.failure.observe(this,
            Observer { failure ->
            Toast.makeText(activity, "failure is $failure", Toast.LENGTH_LONG).show()
        })

        moviesListViewModel.loadMovies()
    }
}
