package com.digian.clean

import com.digian.clean.features.core.data.platform.NetworkHandler
import com.digian.clean.features.movies.data.api.API_KEY
import com.digian.clean.features.movies.data.api.MoviesAPI
import com.digian.clean.features.movies.presentation.MovieDetailViewModel
import com.digian.clean.features.movies.presentation.MoviesListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val THE_MOVIE_DATABASE = "https://api.themoviedb.org/3/"

val appModules = module {

    single {
        createWebService<MoviesAPI>(API_KEY)
    }
//    single {
//        MoviesRepository(get(),get(), androidContext().applicationContext.getString(R.string.language))
//    }
    single {
        NetworkHandler(androidContext())
    }

    //factory { WordLookUpUseCase(get()) }

    viewModel { MoviesListViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}

/* function to build our Retrofit service */
inline fun <reified T> createWebService( baseUrl: String): T {

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    return retrofit.create(T::class.java)
}
