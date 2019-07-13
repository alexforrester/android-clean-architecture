package com.digian.clean.features.movies.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digian.clean.features.core.domain.exception.Failure
import com.digian.clean.features.core.domain.ports.UseCaseInput
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.usecases.GetMovieDetailUseCase

/**
 * Created by Alex Forrester on 23/04/2019.
 */
open class MovieDetailViewModel(val getMovieDetailUseCase: GetMovieDetailUseCase) : ViewModel() {

    val failure: MutableLiveData<Failure> = MutableLiveData()
    val movie: MutableLiveData<MovieEntity> = MutableLiveData()

    //TODO("Add coroutines to run off main thread")
    fun loadMovie(movieId : Int) {
        getMovieDetailUseCase(UseCaseInput.Single(movieId)).successOrError(::handleFailure, ::handleSuccess)
    }

    private fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }

    private fun handleSuccess(movie: MovieEntity) {
        this.movie.value = movie
    }
}