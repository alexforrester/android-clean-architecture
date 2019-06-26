package com.digian.example.moshicodegen.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digian.example.moshicodegen.data.Movie
import com.digian.sample.clean.R
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * Created by Alex Forrester on 24/04/2019.
 */
class MoviesListFragment : Fragment() {

    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
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
        moviesAdapter = MoviesAdapter(object : OnItemClickListener {
            override fun onItemClick(movie: Movie) {
                val action = MoviesListFragmentDirections.actionMoviesFragmentToMovieDetailFragment(movie.id)
                findNavController().navigate(action)
            }
        })

        moviesRecyclerView = movies_recycler_view.apply {
            setHasFixedSize(true)

            layoutManager = moviesViewManager
            adapter = moviesAdapter

            addItemDecoration(DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation))
        }

        moviesListViewModel.getMovies().observe(this,
            Observer<List<Movie>> { popularMovies ->
                moviesAdapter.data = popularMovies
            })
    }
}
