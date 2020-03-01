package com.digian.clean.features.movies.presentation

import androidx.lifecycle.Observer
import com.digian.clean.InstantExecutorExtension
import com.digian.clean.MovieRepositoryFactory
import com.digian.clean.MoviesLifeCycleOwner
import com.digian.clean.core.domain.exception.Failure
import com.digian.clean.features.movies.domain.entities.GenreEntity
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.usecases.GetMovieDetailUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Created by Alex Forrester on 2019-04-24.
 *
 * TODO("Fix failing coroutines tests")
 */
@ExtendWith(InstantExecutorExtension::class)
internal class MovieDetailViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val moviesDetailViewModel: MovieDetailViewModel = MovieDetailViewModel(
        GetMovieDetailUseCase(MovieRepositoryFactory.movieRepository)
    )

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun `given valid movie id, when used to retrieve movie, then movie state returned correctly`() {

        val observer = mockk<Observer<MovieEntity>>()
        every{ observer.onChanged(any()) } just Runs

        moviesDetailViewModel.movie.observe(MoviesLifeCycleOwner(), observer)
        moviesDetailViewModel.loadMovie(278)

        verify { observer.onChanged(any()) }
        verify { observer.onChanged(ofType(MovieEntity::class))}
        verify { observer.onChanged(match { it.title == "The Shawshank Redemption" })}
        verify { observer.onChanged(match { it.voteCount == 12691 })}
        verify { observer.onChanged(match { it.genres == listOf(
            GenreEntity(
                18,
                "Drama"
            ), GenreEntity(80, "Crime")
        ) })}
        verify { observer.onChanged(match { it.overview == "Framed in the 1940s for the double murder of his wife and her lover, " +
                "upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. " +
                "During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- " +
                "for his integrity and unquenchable sense of hope." })}

        confirmVerified(observer)
    }

    @Test
    fun `given invalid movie id, when used to retrieve movie, then movie state not set`() {

        val observer = mockk<Observer<MovieEntity>>()
        val failureObserver = mockk<Observer<Failure>>()

        every { observer.onChanged(any()) } just Runs
        every { failureObserver.onChanged(any()) } just Runs

        //Verifying observer called when no movie found
        moviesDetailViewModel.movie.observe(MoviesLifeCycleOwner(), observer)
        moviesDetailViewModel.failure.observe(MoviesLifeCycleOwner(), failureObserver)
        moviesDetailViewModel.loadMovie(UNKNOWN_MOVIE_ID)

        //failureObserver
        verify { failureObserver.onChanged(any())}

        confirmVerified(observer)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

}