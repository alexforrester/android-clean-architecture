package com.digian.clean.features.movies.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digian.clean.core.domain.exception.Failure
import com.digian.clean.core.domain.ports.UseCaseInputPort
import com.digian.clean.core.domain.ports.UseCaseOutputPort
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.usecases.MoviesUseCase
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * Created by Alex Forrester on 23/04/20
 */
class MoviesListViewModel(val moviesUseCase: MoviesUseCase) : ViewModel() {

    val failure: MutableLiveData<Failure> = MutableLiveData()
    val movies: MutableLiveData<List<MovieEntity>> = MutableLiveData()

    fun loadMovies() {
        viewModelScope.launch {
            val useCaseOutputDeferred = async { getMovieList() }
            val useCaseOutputPort = useCaseOutputDeferred.await()
            useCaseOutputPort.successOrError(::handleFailure, ::handleSuccess)
        }
    }

    private suspend fun getMovieList(): UseCaseOutputPort<Failure, List<MovieEntity>> =
        withContext(Dispatchers.IO) {
            moviesUseCase(UseCaseInputPort.None)
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