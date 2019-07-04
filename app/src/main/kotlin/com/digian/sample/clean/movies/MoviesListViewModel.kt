package com.digian.sample.clean.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.digian.sample.clean.movies.data.model.MovieData
import com.digian.sample.clean.movies.data.PopularMoviesRepository
import com.digian.sample.clean.movies.data.MoviesRepositoryImpl


/**
 * Created by Alex Forrester on 23/04/2019.
 */
open class MoviesListViewModel(application: Application) : AndroidViewModel(application) {

    private val popularMoviesRepository: PopularMoviesRepository = getRepository()

    fun getMovies() : LiveData<List<MovieData>> {
        return Transformations.map(popularMoviesRepository.getMovies()) {
                movieList -> movieList.sortedByDescending { it.voteCount}
        }
    }


    internal open fun getRepository() : PopularMoviesRepository {
        return MoviesRepositoryImpl(getApplication())
    }
}