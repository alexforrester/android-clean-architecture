package com.digian.clean.features.movies.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.digian.clean.features.core.data.exception.Failures
import com.digian.clean.features.core.domain.usecases.BaseUseCase
import com.digian.clean.features.movies.data.repository.PopularMoviesRepositoryImpl
import com.digian.clean.features.movies.domain.repository.PopularMoviesRepository
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.usecases.GetMoviesUseCase


/**
 * Created by Alex Forrester on 23/04/2019.
 */
open class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val getMoviesUseCase: GetMoviesUseCase = GetMoviesUseCase(getRepository())
    private val movie: MutableLiveData<MovieEntity> = MutableLiveData()
    private var movieId = -1

    fun getMovie(movieId : Int) : LiveData<MovieEntity>{
        this.movieId = movieId
        return movie
    }

    fun loadMovie() {
        getMoviesUseCase(BaseUseCase.None()).successOrError(::handleFailure, ::handleSuccess)
    }

    internal open fun getRepository() : PopularMoviesRepository {
        return PopularMoviesRepositoryImpl(
            getApplication()
        )
    }

    private fun handleFailure(failures: Failures) {

    }

    private fun handleSuccess(movies: List<MovieEntity>) {

        this.movie.value = movies.find {
            it.id == movieId
        }
    }
}