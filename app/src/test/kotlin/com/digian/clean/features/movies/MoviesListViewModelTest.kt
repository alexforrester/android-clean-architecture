package com.digian.clean.features.movies

import androidx.lifecycle.Observer
import com.digian.clean.InstantExecutorExtension
import com.digian.clean.MoviesLifeCycleOwner
import com.digian.clean.features.movies.data.ASSET_BASE_PATH
import com.digian.clean.features.movies.data.PopularMoviesRepositoryImpl
import com.digian.clean.features.movies.domain.PopularMoviesRepository
import com.digian.clean.features.movies.domain.entities.MovieEntity
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.FileInputStream
import java.io.InputStream

/**
 * Created by Alex Forrester on 2019-04-24.
 */
@ExtendWith(InstantExecutorExtension::class)
internal class MoviesListViewModelTest {

    private val moviesListViewModel: MoviesListViewModel = object : MoviesListViewModel(mockk()) {

        override fun getRepository() : PopularMoviesRepository = object :
            PopularMoviesRepositoryImpl(mockk()) {

            override fun getInputStreamForJsonFile(fileName: String): InputStream {
                return FileInputStream(ASSET_BASE_PATH + fileName)
            }
        }
    }

    @Test
    fun `given getMovies call made, when live data isInitialised, then adding observer emits onChanged`() {

        val observer = mockk<Observer<List<MovieEntity>>>()
        every { observer.onChanged(any()) } just Runs

        moviesListViewModel.movies.observe(MoviesLifeCycleOwner(), observer)
        moviesListViewModel.loadMovies()

        verify { observer.onChanged(any()) }
        verify {
            observer.onChanged(match { it.size == 20 })
        }

        confirmVerified(observer)
    }
}