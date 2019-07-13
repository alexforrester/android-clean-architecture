package com.digian.clean.features.movies.presentation

import androidx.lifecycle.Observer
import com.digian.clean.InstantExecutorExtension
import com.digian.clean.MovieRepositoryFactory
import com.digian.clean.MoviesLifeCycleOwner
import com.digian.clean.features.core.data.exception.Failures
import com.digian.clean.features.core.domain.exception.Failure
import com.digian.clean.features.core.domain.ports.UseCaseInput
import com.digian.clean.features.core.domain.ports.UseCaseOutput
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.repository.MoviesRepository
import com.digian.clean.features.movies.domain.usecases.GetMoviesUseCase
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Created by Alex Forrester on 2019-04-24.
 */
@ExtendWith(InstantExecutorExtension::class)
internal class MoviesListViewModelTest {

    private val moviesListViewModel: MoviesListViewModel = MoviesListViewModel(
        GetMoviesUseCase(MovieRepositoryFactory.movieRepository)
    )

    private val moviesListViewModelWithFailureRepoGetMovies: MoviesListViewModel = MoviesListViewModel(
        GetMoviesUseCase(MovieRepositoryFactory.movieRepositoryFailure)
    )

    @Test
    fun `given getMovies call made, when a successful use case returns, then adding observer emits onChanged`() {

        val observer = mockk<Observer<List<MovieEntity>>>()
        every { observer.onChanged(any()) } just Runs

        moviesListViewModel.movies.observe(MoviesLifeCycleOwner(), observer)
        moviesListViewModel.loadMovies()

        verify { observer.onChanged(any()) }
        verify {
            observer.onChanged(match {
                it.size == 20
            }
            )
        }
        verify {
            observer.onChanged(match {
                it[0].title == "The Dark Knight"
            }
            )
        }
        verify {
            observer.onChanged(match {
                it[0].voteCount == 18378
            }
            )
        }

        confirmVerified(observer)
    }

    @Test
    fun `given getMovies call made, when a failure use case returns of server error, then adding observer emits onChanged`() {

        val observer = mockk<Observer<List<MovieEntity>>>()
        every { observer.onChanged(any()) } just Runs

        val failureObserver = mockk<Observer<Failure>>()
        every { failureObserver.onChanged(any()) } just Runs

        val mockRepository = mockk<MoviesRepository>()
        every { mockRepository.getMovies(UseCaseInput.None) } returns UseCaseOutput.Error(
            Failures.ServerException(
                Exception()
            )
        )

        moviesListViewModelWithFailureRepoGetMovies.failure.observe(
            MoviesLifeCycleOwner(),
            failureObserver
        )
        moviesListViewModelWithFailureRepoGetMovies.movies.observe(MoviesLifeCycleOwner(), observer)
        moviesListViewModelWithFailureRepoGetMovies.loadMovies()

        verify { failureObserver.onChanged(any()) }
        verify {
            failureObserver.onChanged(match {
                it is Failures.ServerException
            }
            )
        }

        confirmVerified(observer)
    }

    @Test
    fun `given getMovies call made, when a failure use case returns of network error, then adding observer emits onChanged`() {

        val observer = mockk<Observer<List<MovieEntity>>>()
        every { observer.onChanged(any()) } just Runs

        val failureObserver = mockk<Observer<Failure>>()
        every { failureObserver.onChanged(any()) } just Runs

        val mockRepository = mockk<MoviesRepository>()
        every { mockRepository.getMovies(UseCaseInput.None) } returns UseCaseOutput.Error(
            Failures.ServerException(
                Exception()
            )
        )

        moviesListViewModelWithFailureRepoGetMovies.failure.observe(
            MoviesLifeCycleOwner(),
            failureObserver
        )
        moviesListViewModelWithFailureRepoGetMovies.movies.observe(MoviesLifeCycleOwner(), observer)
        moviesListViewModelWithFailureRepoGetMovies.loadMovies()

        verify { failureObserver.onChanged(any()) }
        verify {
            failureObserver.onChanged(match {
                it is Failures.ServerException
            }
            )
        }

        confirmVerified(observer)
    }

}