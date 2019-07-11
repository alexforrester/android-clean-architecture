package com.digian.clean.features.movies.domain.usecases

import com.digian.clean.features.core.domain.UseCaseResult
import com.digian.clean.features.core.domain.exception.Failure
import com.digian.clean.features.core.domain.usecases.BaseUseCase
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.repository.PopularMoviesRepository

open class GetMoviesUseCase(private val popularMoviesRepository: PopularMoviesRepository) : BaseUseCase<BaseUseCase.None, List<MovieEntity>>() {

    override fun run(params: None): UseCaseResult<Failure, List<MovieEntity>> = popularMoviesRepository.getMovies()
}