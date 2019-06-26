package com.digian.example.moshicodegen.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import java.io.InputStream

/**
 * Created by Alex Forrester on 17/04/2019.
 *
 * Read in a flat file json list of movies from Assets folder and deserializes to list of movies before creating LiveData object
 *
 * The Live Data object is initialised with a value when {@link #getMovies() getMovies} is called which will be emitted when observer added
 *
 * No caching or optimisation is preformed for this example
 *
 */
internal interface PopularMoviesRepository {
    fun getMovies(): LiveData<List<Movie>>
}

internal object MoshiFactory {

    private val moshi : Moshi =  Moshi.Builder()
        .add(GenreAdapter())
        .build()

    fun getInstance() = moshi
}

internal open class PopularMoviesRepositoryImpl(
    private val context: Context,
    private val moshi: Moshi = MoshiFactory.getInstance()) : PopularMoviesRepository {

    private val moviesLiveData = MutableLiveData<List<Movie>>()

    /**
     * Sets and returns the LiveData object so observers will be notified of the last change
     */
    override fun getMovies() : LiveData<List<Movie>> {

        val moviesJson = getMovieJSON()

        val listType = Types.newParameterizedType(List::class.java, Movie::class.java)
        val adapter: JsonAdapter<List<Movie>> = moshi.adapter(listType)
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