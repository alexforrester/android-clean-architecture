package com.digian.clean.features.movies.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digian.clean.features.core.domain.exception.Failure
import com.digian.clean.features.core.domain.ports.UseCaseInput
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.usecases.GetMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Created by Alex Forrester on 23/04/20
 */
open class MoviesListViewModel(val getMoviesUseCase: GetMoviesUseCase) : ViewModel() {

    val failure: MutableLiveData<Failure> = MutableLiveData()
    val movies: MutableLiveData<List<MovieEntity>> = MutableLiveData()

    fun loadMovies() {
        viewModelScope.launch {
            val useCaseOutputDeferred = async { get() }
            val useCaseOutput = useCaseOutputDeferred.await()
            useCaseOutput.successOrError(::handleFailure, ::handleSuccess)
        }
    }

    suspend fun get() =
        withContext(Dispatchers.IO) {
            // Dispatchers.IO (main-safety block)
            getMoviesUseCase(UseCaseInput.None)
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