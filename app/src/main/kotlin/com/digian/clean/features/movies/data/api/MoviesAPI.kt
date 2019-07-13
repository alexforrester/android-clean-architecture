package com.digian.clean.features.movies.data.api

import com.digian.clean.features.movies.data.MovieData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "b76ab90a5c6dad4f14b525aed728e6a7"

interface MoviesAPI {

    @GET("discover/movie/")
    suspend fun getPopularMovies(@Query("sort_by") sort_by: String, @Query("api_key") apiKey: String): Response<List<MovieData>>
}