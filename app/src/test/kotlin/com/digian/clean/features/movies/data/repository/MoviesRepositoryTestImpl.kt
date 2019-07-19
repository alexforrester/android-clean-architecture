package com.digian.clean.features.movies.data.repository

import com.digian.clean.InstantExecutorExtension
import com.digian.clean.MoshiFactory
import com.digian.clean.core.data.exception.Failures
import com.digian.clean.core.data.platform.NetworkHandler
import com.digian.clean.core.domain.ports.UseCaseInput
import com.digian.clean.features.movies.domain.entities.GenreEntity
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.repository.MoviesRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.fail
import org.junit.jupiter.api.function.Executable
import java.io.FileInputStream
import java.io.InputStream

/**
 * Created by Alex Forrester on 11/04/2019.
 */
const val ASSET_BASE_PATH = "../app/src/main/assets/"

@ExtendWith(InstantExecutorExtension::class)
internal class MoviesRepositoryTest {

    private val networkHandlerConnected: NetworkHandler = mockk()
    private val networkHandlerNotConnected: NetworkHandler = mockk()
    private val moshi = MoshiFactory.moshi

    init {
        every { networkHandlerConnected.isConnected } returns true
        every { networkHandlerNotConnected.isConnected } returns false
    }

    private val moviesRepository: MoviesRepository = object :
        MoviesRepositoryImpl(mockk(), moshi, networkHandler = networkHandlerConnected) {

        override suspend fun getInputStreamForJsonFile(fileName: String): InputStream {
            return FileInputStream(ASSET_BASE_PATH + fileName)
        }
    }

    private val moviesRepositoryNoNetwork: MoviesRepository = object :
        MoviesRepositoryImpl(mockk(), moshi, networkHandler = networkHandlerNotConnected) {

        override suspend fun getInputStreamForJsonFile(fileName: String): InputStream {
            return FileInputStream(ASSET_BASE_PATH + fileName)
        }
    }

    @Test
    internal fun `given live data movie list is called, when no network connection, then Network Unavailable message returned`() {

        val popularMovies = runBlocking {  moviesRepositoryNoNetwork.getMovies(UseCaseInput.None)}

        popularMovies.successOrError({
            assertTrue(it is Failures.NetworkUnavailable)

        }, {
            fail("Movie list returned")
        }
        )
    }

    @Test
    internal fun `given live data movie detail is called, when no network connection, then Network Unavailable message returned`() {

        val popularMovies =

            runBlocking { moviesRepositoryNoNetwork.getMovieDetail(UseCaseInput.Single(278)) }

        popularMovies.successOrError({
            assertTrue(it is Failures.NetworkUnavailable)
        }, {
            fail("Movie returned")
        }
        )
    }

    @Test
    internal fun `given live data movie list is called, when flat json file parsed, then individual movie has correct state`() {

        val popularMovies = runBlocking {  moviesRepository.getMovies(UseCaseInput.None) }

        popularMovies.successOrError({

            fail("Movie list not returned")
        }, {

            val movie: MovieEntity = it[1]

            assertAll(

                //Test Individual film
                Executable { assertEquals(12691, movie.voteCount) },
                Executable { assertEquals(278, movie.id) },
                Executable { assertEquals("The Shawshank Redemption", movie.title) },
                Executable {
                    assertEquals(
                        listOf(
                            GenreEntity(18, "Drama"),
                            GenreEntity(80, "Crime")
                        ), movie.genres
                    )
                },
                Executable {
                    assertEquals(
                        "Framed in the 1940s for the double murder of his wife and her lover, " +
                                "upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. " +
                                "During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- " +
                                "for his integrity and unquenchable sense of hope.", movie.overview
                    )
                }
            )
        }


        )

    }
}









