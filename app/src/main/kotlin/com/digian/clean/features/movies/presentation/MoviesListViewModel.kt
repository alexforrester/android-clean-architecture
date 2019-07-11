package com.digian.clean.features.movies.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.digian.clean.features.core.data.exception.Failures
import com.digian.clean.features.core.domain.exception.Failure
import com.digian.clean.features.core.domain.usecases.BaseUseCase
import com.digian.clean.features.movies.data.repository.PopularMoviesRepositoryImpl
import com.digian.clean.features.movies.domain.repository.PopularMoviesRepository
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.usecases.GetMoviesUseCase


/**
 * Created by Alex Forrester on 23/04/20
 */
open class MoviesListViewModel(application: Application) : AndroidViewModel(application) {

    private val getMoviesUseCase: GetMoviesUseCase = GetMoviesUseCase(getRepository())
    val failures: MutableLiveData<Failure> = MutableLiveData()
    val movies: MutableLiveData<List<MovieEntity>> = MutableLiveData()

    internal open fun getRepository(): PopularMoviesRepository {
        return PopularMoviesRepositoryImpl(
            getApplication()
        )
    }

    fun loadMovies() {
        getMoviesUseCase(BaseUseCase.None()).successOrError(::handleFailure, ::handleSuccess)
    }

    private fun handleFailure(failure: Failure) {
        //TODO Add Error handling
        Log.d(this.javaClass.name, failures.toString())
        this.failures.value = failure

    }

    private fun handleSuccess(movies: List<MovieEntity>) {
        val sortedMovies = movies.sortedByDescending {
            it.voteCount
        }

        this.movies.value = sortedMovies
    }


}