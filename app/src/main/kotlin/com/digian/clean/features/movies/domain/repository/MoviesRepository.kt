package com.digian.clean.features.movies.domain.repository

import com.digian.clean.core.domain.exception.Failure
import com.digian.clean.core.domain.ports.UseCaseInputPort
import com.digian.clean.core.domain.ports.UseCaseOutputPort
import com.digian.clean.features.movies.domain.entities.MovieEntity

interface MoviesRepository {
    suspend fun getMovies(none: UseCaseInputPort.None): UseCaseOutputPort<Failure, List<MovieEntity>>
    suspend fun getMovieDetail(movieIdInputPort: UseCaseInputPort.Single<Int>): UseCaseOutputPort<Failure, MovieEntity>
}
