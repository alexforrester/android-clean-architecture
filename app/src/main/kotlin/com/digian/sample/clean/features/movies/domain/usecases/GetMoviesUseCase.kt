package com.digian.sample.clean.features.movies.domain.usecases

import com.digian.sample.clean.core.domain.UseCaseResult
import com.digian.sample.clean.core.domain.exception.Failure
import com.digian.sample.clean.core.domain.usecases.BaseUseCase
import com.digian.sample.clean.features.movies.data.PopularMoviesRepository
import com.digian.sample.clean.features.movies.data.model.MovieData

open class GetMoviesUseCase(private val moviesRepository: PopularMoviesRepository) : BaseUseCase<BaseUseCase.None, List<MovieData>>() {

    override fun run(params: None): UseCaseResult<Failure, List<MovieData>> = moviesRepository.getMovies()
}