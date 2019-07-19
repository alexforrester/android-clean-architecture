package com.digian.clean.features.movies.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digian.clean.core.domain.exception.Failure
import com.digian.clean.core.domain.ports.UseCaseInput
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.usecases.GetMovieDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Alex Forrester on 23/04/2019.
 */
open class MovieDetailViewModel(val getMovieDetailUseCase: GetMovieDetailUseCase) : ViewModel() {

    val failure: MutableLiveData<Failure> = MutableLiveData()
    val movie: MutableLiveData<MovieEntity> = MutableLiveData()

    fun loadMovie(movieId : Int) {
        viewModelScope.launch {
            val useCaseOutputDeferred = async { get(movieId) }
            val useCaseOutput = useCaseOutputDeferred.await()
            useCaseOutput.successOrError(::handleFailure, ::handleSuccess)
        }
    }

    suspend fun get(movieId: Int) =
        withContext(Dispatchers.IO) {
            // Dispatchers.IO (main-safety block)
            getMovieDetailUseCase(UseCaseInput.Single(movieId))
        }

    private fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }

    private fun handleSuccess(movie: MovieEntity) {
        this.movie.value = movie
    }
}