package com.digian.clean.features.movies.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digian.clean.features.core.domain.exception.Failure
import com.digian.clean.features.core.domain.ports.UseCaseInput
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.usecases.GetMoviesUseCase
import timber.log.Timber

/**
 * Created by Alex Forrester on 23/04/20
 */
open class MoviesListViewModel(val getMoviesUseCase: GetMoviesUseCase) : ViewModel() {

    val failure: MutableLiveData<Failure> = MutableLiveData()
    val movies: MutableLiveData<List<MovieEntity>> = MutableLiveData()

    //TODO("Add coroutines to run off main thread")
    fun loadMovies() {
        getMoviesUseCase(UseCaseInput.None).successOrError(::handleFailure, ::handleSuccess)
    }

    private fun handleFailure(failure: Failure) {
        Timber.d(this.failure.toString())
        this.failure.value = failure
    }

    private fun handleSuccess(movies: List<MovieEntity>) {
        val sortedMovies = movies.sortedByDescending {
            it.voteCount
        }

        this.movies.value = sortedMovies
    }
}