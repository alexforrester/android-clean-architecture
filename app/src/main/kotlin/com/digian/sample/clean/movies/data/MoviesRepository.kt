package com.digian.sample.clean.movies.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.digian.sample.clean.movies.data.model.MovieData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import java.io.InputStream

internal interface PopularMoviesRepository {
    fun getMovies(): LiveData<List<MovieData>>
}

internal object MoshiFactory {

    private val moshi : Moshi =  Moshi.Builder()
        .add(GenreAdapter())
        .build()

    fun getInstance() = moshi
}

internal open class MoviesRepositoryImpl(
    private val context: Context,
    private val moshi: Moshi = MoshiFactory.getInstance()) : PopularMoviesRepository {

    private val moviesLiveData = MutableLiveData<List<MovieData>>()

    /**
     * Sets and returns the LiveData object so observers will be notified of the last change
     */
    override fun getMovies() : LiveData<List<MovieData>> {

        val moviesJson = getMovieJSON()

        val listType = Types.newParameterizedType(List::class.java, MovieData::class.java)
        val adapter: JsonAdapter<List<MovieData>> = moshi.adapter(listType)
        val result = adapter.fromJson(moviesJson)

        moviesLiveData.value = result

        return moviesLiveData
    }

    private fun getMovieJSON(fileName : String = "popular_movies_list.json"): String {
        val inputStream = getInputStreamForJsonFile(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }

    @Throws(IOException::class)
    internal open fun getInputStreamForJsonFile(fileName: String): InputStream {
        return context.assets.open(fileName)
    }
}