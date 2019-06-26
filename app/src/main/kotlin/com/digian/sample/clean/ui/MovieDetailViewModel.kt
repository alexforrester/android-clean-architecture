package com.digian.example.moshicodegen.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.digian.example.moshicodegen.data.Movie
import com.digian.example.moshicodegen.data.PopularMoviesRepository
import com.digian.example.moshicodegen.data.PopularMoviesRepositoryImpl


/**
 * Created by Alex Forrester on 23/04/2019.
 */
open class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val popularMoviesRepository: PopularMoviesRepository = getRepository()

    fun getMovie(movieId : Int) : LiveData<Movie> {

        return Transformations.map(popularMoviesRepository.getMovies()) {
            movieList -> movieList.find { it.id == movieId}
        }
    }

    internal open fun getRepository() : PopularMoviesRepository {
        return PopularMoviesRepositoryImpl(getApplication())
    }
}