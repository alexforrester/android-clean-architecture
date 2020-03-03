package com.digian.clean.features.movies.domain.usecases

import com.digian.clean.core.domain.exception.Failure
import com.digian.clean.core.domain.ports.UseCaseInputPort
import com.digian.clean.core.domain.ports.UseCaseOutputPort
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.repository.MoviesRepository

open class MoviesUseCase(private val moviesRepository: MoviesRepository) {

   suspend operator fun invoke(none: UseCaseInputPort.None): UseCaseOutputPort<Failure, List<MovieEntity>> = moviesRepository.getMovies(none)
}