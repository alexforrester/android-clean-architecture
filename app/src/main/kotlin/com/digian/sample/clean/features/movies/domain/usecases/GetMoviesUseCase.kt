package com.digian.sample.clean.features.movies.domain.usecases

import com.digian.sample.clean.core.domain.UseCaseResult
import com.digian.sample.clean.core.domain.exception.Failure
import com.digian.sample.clean.core.domain.usecases.BaseUseCase
import com.digian.sample.clean.features.movies.domain.PopularMoviesRepository
import com.digian.sample.clean.features.movies.domain.entities.MovieEntity

open class GetMoviesUseCase(private val moviesRepository: PopularMoviesRepository) : BaseUseCase<BaseUseCase.None, List<MovieEntity>>() {

    override fun run(params: None): UseCaseResult<Failure, List<MovieEntity>> = moviesRepository.getMovies()
}