package com.digian.example.moshicodegen.ui

import androidx.lifecycle.Observer
import com.digian.example.moshicodegen.InstantExecutorExtension
import com.digian.example.moshicodegen.MoviesLifeCycleOwner
import com.digian.example.moshicodegen.data.ASSET_BASE_PATH
import com.digian.example.moshicodegen.data.Movie
import com.digian.example.moshicodegen.data.PopularMoviesRepository
import com.digian.example.moshicodegen.data.PopularMoviesRepositoryImpl
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

        override fun getRepository() :  PopularMoviesRepository = object :
            PopularMoviesRepositoryImpl(mockk()) {

            override fun getInputStreamForJsonFile(fileName: String): InputStream {
                return FileInputStream(ASSET_BASE_PATH + fileName)
            }
        }
    }

    @Test
    fun `given getMovies call made, when live data isInitialised, then adding observer emits onChanged`() {

        val observer = mockk<Observer<List<Movie>>>()
        every { observer.onChanged(any()) } just Runs

        moviesListViewModel.getMovies().observe(MoviesLifeCycleOwner(), observer)

        verify { observer.onChanged(any()) }
        verify {
            observer.onChanged(match { it.size == 20 })
        }

        confirmVerified(observer)
    }
}