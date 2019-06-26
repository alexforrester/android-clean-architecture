package com.digian.example.moshicodegen.data

import androidx.lifecycle.Observer
import com.digian.example.moshicodegen.InstantExecutorExtension
import com.digian.example.moshicodegen.MoviesLifeCycleOwner
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import java.io.FileInputStream
import java.io.InputStream

/**
 * Created by Alex Forrester on 11/04/2019.
 */
const val ASSET_BASE_PATH = "../app/src/main/assets/"

@ExtendWith(InstantExecutorExtension::class)
internal class PopularPopularMoviesRepositoryTest {

    private val popularMoviesRepository: PopularMoviesRepository = object :
        PopularMoviesRepositoryImpl(mockk()) {

        override fun getInputStreamForJsonFile(fileName: String): InputStream {
            return FileInputStream(ASSET_BASE_PATH + fileName)
        }
    }

    @Test
    internal fun `given live data movie list is initialised, when observer added, then observer notified`() {

        val observer = mockk<Observer<List<Movie>>>()
        every{ observer.onChanged(any()) } just Runs

        popularMoviesRepository.getMovies().observe(MoviesLifeCycleOwner(), observer)

        verify {observer.onChanged(any()) }
    }

    @Test
    internal fun `given live data movie list is called, when flat json file parsed, then movie list created`() {

        val popularMovies = popularMoviesRepository.getMovies().value

        assertAll(
            Executable { assertEquals(20, popularMovies?.size) }
        )
    }

    @Test
    internal fun `given live data movie list is called, when flat json file parsed, then individual movie has correct state`() {

        val popularMovies = popularMoviesRepository.getMovies().value

        val movie = popularMovies!![1]

        assertAll(

            //Test Individual film
            Executable { assertEquals(12691, movie.voteCount) },
            Executable { assertEquals(278, movie.id) },
            Executable { assertEquals("The Shawshank Redemption", movie.title) },
            Executable { assertEquals(listOf(Genre(18,"Drama"),Genre(80, "Crime")), movie.genres) },
            Executable { assertEquals("Framed in the 1940s for the double murder of his wife and her lover, " +
                    "upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. " +
                    "During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- " +
                    "for his integrity and unquenchable sense of hope.", movie.overview) }
        )
    }
}









