package com.digian.clean.features.movies.domain.usecases

import com.digian.clean.core.domain.exception.Failure
import com.digian.clean.core.domain.usecases.UseCaseInput
import com.digian.clean.core.domain.usecases.UseCaseOutput
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.repository.MoviesRepository

open class GetMoviesUseCase(private val moviesRepository: MoviesRepository) {

   suspend operator fun invoke(none: UseCaseInput.None): UseCaseOutput<Failure, List<MovieEntity>> = moviesRepository.getMovies(none)
}