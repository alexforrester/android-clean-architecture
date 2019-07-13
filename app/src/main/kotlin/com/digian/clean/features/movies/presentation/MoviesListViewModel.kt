package com.digian.clean.features.movies.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.digian.clean.features.core.data.platform.NetworkHandler
import com.digian.clean.features.core.domain.exception.Failure
import com.digian.clean.features.core.domain.ports.UseCaseInput
import com.digian.clean.features.movies.data.repository.MoviesRepositoryImpl
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.repository.MoviesRepository
import com.digian.clean.features.movies.domain.usecases.GetMoviesUseCase
import timber.log.Timber

/**
 * Created by Alex Forrester on 23/04/20
 */
open class MoviesListViewModel(application: Application) : AndroidViewModel(application) {

    private val getMoviesUseCase: GetMoviesUseCase = GetMoviesUseCase(getRepository())
    val failure: MutableLiveData<Failure> = MutableLiveData()
    val movies: MutableLiveData<List<MovieEntity>> = MutableLiveData()

    internal open fun getRepository(): MoviesRepository {
        return MoviesRepositoryImpl(
            getApplication(),
            networkHandler = NetworkHandler(getApplication())
        )
    }

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