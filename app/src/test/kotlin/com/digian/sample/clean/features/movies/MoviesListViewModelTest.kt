package com.digian.sample.clean.features.movies

import androidx.lifecycle.Observer
import com.digian.sample.clean.InstantExecutorExtension
import com.digian.sample.clean.MoviesLifeCycleOwner
import com.digian.sample.clean.features.movies.data.ASSET_BASE_PATH
import com.digian.sample.clean.features.movies.data.model.MovieData
import com.digian.sample.clean.features.movies.data.PopularMoviesRepository
import com.digian.sample.clean.features.movies.data.MoviesRepositoryImpl
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
            MoviesRepositoryImpl(mockk()) {

            override fun getInputStreamForJsonFile(fileName: String): InputStream {
                return FileInputStream(ASSET_BASE_PATH + fileName)
            }
        }
    }

    @Test
    fun `given getMovies call made, when live data isInitialised, then adding observer emits onChanged`() {

        val observer = mockk<Observer<List<MovieData>>>()
        every { observer.onChanged(any()) } just Runs

        moviesListViewModel.getMovies().observe(MoviesLifeCycleOwner(), observer)
        moviesListViewModel.loadMovies()

        verify { observer.onChanged(any()) }
        verify {
            observer.onChanged(match { it.size == 20 })
        }

        confirmVerified(observer)
    }
}