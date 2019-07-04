package com.digian.sample.clean.features.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digian.sample.clean.core.domain.exception.Failure
import com.digian.sample.clean.core.domain.usecases.BaseUseCase
import com.digian.sample.clean.features.movies.data.MoviesRepositoryImpl
import com.digian.sample.clean.features.movies.data.PopularMoviesRepository
import com.digian.sample.clean.features.movies.data.model.MovieData
import com.digian.sample.clean.features.movies.domain.usecases.GetMoviesUseCase


/**
 * Created by Alex Forrester on 23/04/20
 * 19.
 */
open class MoviesListViewModel(application: Application) : AndroidViewModel(application) {

    private val getMoviesUseCase: GetMoviesUseCase = GetMoviesUseCase(getRepository())
    private val failure: MutableLiveData<Failure> = MutableLiveData()
    private val movies: MutableLiveData<List<MovieData>> = MutableLiveData()

    internal open fun getRepository() : PopularMoviesRepository {
        return MoviesRepositoryImpl(getApplication())
    }

    fun getMovies(): LiveData<List<MovieData>> {
        return movies
    }

    fun loadMovies() {
        getMoviesUseCase(BaseUseCase.None()).successOrError(::handleFailure, ::handleSuccess)
    }

    private fun handleFailure(failure: Failure) {
        //TODO Add Error handling
    }

    private fun handleSuccess(movies: List<MovieData>) {
        this.movies.value = movies
    }




}