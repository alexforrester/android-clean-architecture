package com.digian.sample.clean.features.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.digian.sample.clean.core.domain.exception.Failure
import com.digian.sample.clean.core.domain.usecases.BaseUseCase
import com.digian.sample.clean.features.movies.data.MoviesRepositoryImpl
import com.digian.sample.clean.features.movies.data.PopularMoviesRepository
import com.digian.sample.clean.features.movies.data.model.MovieData
import com.digian.sample.clean.features.movies.domain.usecases.GetMoviesUseCase


/**
 * Created by Alex Forrester on 23/04/2019.
 */
open class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val getMoviesUseCase: GetMoviesUseCase = GetMoviesUseCase(getRepository())
    private val movie: MutableLiveData<MovieData> = MutableLiveData()
    private var movieId = 0

    fun getMovie(movieId : Int) : LiveData<MovieData>{
        this.movieId = movieId
        return movie
    }

    fun loadMovie() {
        getMoviesUseCase(BaseUseCase.None()).successOrError(::handleFailure, ::handleSuccess)
    }

    internal open fun getRepository() : PopularMoviesRepository {
        return MoviesRepositoryImpl(getApplication())
    }

    private fun handleFailure(failure: Failure) {

    }

    private fun handleSuccess(movies: List<MovieData>) {

        this.movie.value = movies.find {
            it.id == movieId
        }
    }
}