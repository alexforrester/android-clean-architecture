package com.digian.clean.features.movies.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.digian.clean.features.core.data.platform.NetworkHandler
import com.digian.clean.features.core.domain.exception.Failure
import com.digian.clean.features.core.domain.ports.UseCaseInput
import com.digian.clean.features.movies.data.repository.MoviesRepositoryImpl
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.repository.MoviesRepository
import com.digian.clean.features.movies.domain.usecases.GetMovieDetailUseCase

/**
 * Created by Alex Forrester on 23/04/2019.
 */
open class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val getMovieDetailUseCase: GetMovieDetailUseCase = GetMovieDetailUseCase(getRepository())
    private val movie: MutableLiveData<MovieEntity> = MutableLiveData()
    private var movieId = -1

    val movieFailure: MutableLiveData<Failure> = MutableLiveData()

    fun getMovie(movieId : Int) : LiveData<MovieEntity>{
        this.movieId = movieId
        return movie
    }

    fun loadMovie() {
        getMovieDetailUseCase(UseCaseInput.Single(movieId)).successOrError(::handleFailure, ::handleSuccess)
    }

    internal open fun getRepository() : MoviesRepository {
        return MoviesRepositoryImpl(
            getApplication(),
            networkHandler = NetworkHandler(getApplication())
        )
    }

    private fun handleFailure(failure: Failure) {
        movieFailure.value = failure
    }

    private fun handleSuccess(movie: MovieEntity) {
        this.movie.value = movie
    }
}