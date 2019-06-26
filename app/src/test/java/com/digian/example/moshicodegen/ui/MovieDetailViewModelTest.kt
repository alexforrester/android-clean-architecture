package com.digian.example.moshicodegen.ui

import androidx.lifecycle.Observer
import com.digian.example.moshicodegen.InstantExecutorExtension
import com.digian.example.moshicodegen.MoviesLifeCycleOwner
import com.digian.example.moshicodegen.data.*
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.FileInputStream
import java.io.InputStream

/**
 * Created by Alex Forrester on 2019-04-24.
 */
@ExtendWith(InstantExecutorExtension::class)
internal class MovieDetailViewModelTest {

    private val moviesDetailViewModel: MovieDetailViewModel = object : MovieDetailViewModel(mockk()) {

        override fun getRepository() : PopularMoviesRepository = object :
            PopularMoviesRepositoryImpl(mockk()) {

            override fun getInputStreamForJsonFile(fileName: String): InputStream {
                return FileInputStream(ASSET_BASE_PATH + fileName)
            }
        }
    }

    @Test
    fun `given valid movie id, when used to retrieve movie, then movie state returned correctly`() {

        val observer = mockk<Observer<Movie>>()
        every{ observer.onChanged(any()) } just Runs

        moviesDetailViewModel.getMovie(278).observe(MoviesLifeCycleOwner(), observer)

        verify { observer.onChanged(any()) }
        verify { observer.onChanged(ofType(Movie::class))}
        verify { observer.onChanged(match { it.title == "The Shawshank Redemption" })}
        verify { observer.onChanged(match { it.voteCount == 12691 })}
        verify { observer.onChanged(match { it.genres == listOf(Genre(18,"Drama"),Genre(80, "Crime")) })}
        verify { observer.onChanged(match { it.overview == "Framed in the 1940s for the double murder of his wife and her lover, " +
                "upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. " +
                "During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- " +
                "for his integrity and unquenchable sense of hope." })}

        confirmVerified(observer)
    }

    @Test
    fun `given invalid movie id, when used to retrieve movie, then movie state not set`() {

        val observer = mockk<Observer<Movie>>()
        every{ observer.onChanged(any()) } just Runs

        //Verifying observer called when no movie found
        moviesDetailViewModel.getMovie(UNKNOWN_MOVIE_ID).observe(MoviesLifeCycleOwner(), observer)

        verify { observer.onChanged(any())}
        verify { observer.onChanged(isNull())}

        confirmVerified(observer)
    }

}