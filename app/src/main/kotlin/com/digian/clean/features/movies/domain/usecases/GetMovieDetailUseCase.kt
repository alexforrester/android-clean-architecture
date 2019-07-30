package com.digian.clean.features.movies.domain.usecases

import com.digian.clean.core.domain.exception.Failure
import com.digian.clean.core.domain.usecases.UseCaseInput
import com.digian.clean.core.domain.usecases.UseCaseOutput
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.repository.MoviesRepository

/**
 * Created by Alex Forrester on 2019-07-12.
 */
class GetMovieDetailUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(movieIdInput: UseCaseInput.Single<Int>): UseCaseOutput<Failure, MovieEntity> = moviesRepository.getMovieDetail(movieIdInput)
}