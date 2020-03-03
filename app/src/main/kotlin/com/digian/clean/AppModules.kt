package com.digian.clean

import com.digian.clean.core.data.platform.NetworkHandler
import com.digian.clean.features.movies.data.adapters.GenreAdapter
import com.digian.clean.features.movies.data.api.MoviesAPI
import com.digian.clean.features.movies.data.repository.MoviesRepositoryImpl
import com.digian.clean.features.movies.domain.repository.MoviesRepository
import com.digian.clean.features.movies.domain.usecases.MovieDetailUseCase
import com.digian.clean.features.movies.domain.usecases.MoviesUseCase
import com.digian.clean.features.movies.presentation.MovieDetailViewModel
import com.digian.clean.features.movies.presentation.MoviesListViewModel
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val THE_MOVIE_DATABASE = "https://api.themoviedb.org/3/"

val appModules = module {

    single {
        createWebService<MoviesAPI>(THE_MOVIE_DATABASE)
    }

    single {
        MoviesRepositoryImpl(androidContext(), get(), get()) as MoviesRepository
    }

    single {
        Moshi.Builder()
            .add(GenreAdapter())
            .build()
    }

    single {
        NetworkHandler(androidContext())
    }

    factory { MovieDetailUseCase(get()) }
    factory { MoviesUseCase(get()) }

    viewModel { MoviesListViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }


}

/* function to build our Retrofit service */
inline fun <reified T> createWebService(baseUrl: String): T {

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    return retrofit.create(T::class.java)
}
