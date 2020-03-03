package com.digian.clean.features.movies.domain.usecases

import com.digian.clean.core.domain.exception.Failure
import com.digian.clean.core.domain.ports.UseCaseInputPort
import com.digian.clean.core.domain.ports.UseCaseOutputPort
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.repository.MoviesRepository

/**
 * Created by Alex Forrester on 2019-07-12.
 */
class MovieDetailUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(movieIdInputPort: UseCaseInputPort.Single<Int>): UseCaseOutputPort<Failure, MovieEntity> = moviesRepository.getMovieDetail(movieIdInputPort)
}