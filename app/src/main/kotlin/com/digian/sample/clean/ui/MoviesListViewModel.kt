package com.digian.sample.clean.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.digian.sample.clean.data.Movie
import com.digian.sample.clean.data.PopularMoviesRepository
import com.digian.sample.clean.data.PopularMoviesRepositoryImpl


/**
 * Created by Alex Forrester on 23/04/2019.
 */
open class MoviesListViewModel(application: Application) : AndroidViewModel(application) {

    private val popularMoviesRepository: PopularMoviesRepository = getRepository()

    fun getMovies() : LiveData<List<Movie>> {
        return popularMoviesRepository.getMovies()
    }

    internal open fun getRepository() : PopularMoviesRepository {
        return PopularMoviesRepositoryImpl(getApplication())
    }
}