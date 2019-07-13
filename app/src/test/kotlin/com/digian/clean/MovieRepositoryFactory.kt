package com.digian.clean

import com.digian.clean.features.core.data.exception.Failures
import com.digian.clean.features.core.data.platform.NetworkHandler
import com.digian.clean.features.core.domain.exception.Failure
import com.digian.clean.features.core.domain.ports.UseCaseInput
import com.digian.clean.features.core.domain.ports.UseCaseOutput
import com.digian.clean.features.movies.data.repository.ASSET_BASE_PATH
import com.digian.clean.features.movies.data.repository.MoviesRepositoryImpl
import com.digian.clean.features.movies.domain.entities.MovieEntity
import io.mockk.every
import io.mockk.mockk
import java.io.FileInputStream
import java.io.InputStream

/**
 * Created by Alex Forrester on 2019-07-13.
 */

internal object MovieRepositoryFactory {

    private val networkHandlerConnected: NetworkHandler = mockk()

    init {
        every { networkHandlerConnected.isConnected } returns true
    }

    val movieRepository = object : MoviesRepositoryImpl(
        mockk(),
        MoshiFactory.moshi,
        networkHandler = networkHandlerConnected
    ) {
        override fun getInputStreamForJsonFile(fileName: String): InputStream {
            return FileInputStream(ASSET_BASE_PATH + fileName)
        }
    }

    val movieRepositoryFailure = object : MoviesRepositoryImpl(
        mockk(),
        MoshiFactory.moshi,
        networkHandler = networkHandlerConnected
    ) {
        override fun getInputStreamForJsonFile(fileName: String): InputStream {
            return FileInputStream(ASSET_BASE_PATH + fileName)
        }

        override fun getMovies(none: UseCaseInput.None): UseCaseOutput<Failure, List<MovieEntity>> {
            return UseCaseOutput.Error(Failures.ServerException(Exception()))
        }
    }
}