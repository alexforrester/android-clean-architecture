package com.digian.sample.clean.features.movies.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.digian.sample.clean.core.domain.UseCaseResult
import com.digian.sample.clean.core.domain.exception.Failure
import com.digian.sample.clean.features.movies.data.model.MovieData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import java.io.InputStream

interface PopularMoviesRepository {
    fun getMovies(): UseCaseResult<Failure, List<MovieData>>
}

internal object MoshiFactory {

    private val moshi: Moshi = Moshi.Builder()
        .add(GenreAdapter())
        .build()

    fun getInstance() = moshi
}

internal open class MoviesRepositoryImpl(
    private val context: Context,
    private val moshi: Moshi = MoshiFactory.getInstance()
) : PopularMoviesRepository {

    override fun getMovies(): UseCaseResult<Failure, List<MovieData>> {

        return try {

            val moviesJson = getMovieJSON()

            val listType = Types.newParameterizedType(List::class.java, MovieData::class.java)
            val adapter: JsonAdapter<List<MovieData>> = moshi.adapter(listType)

            val movies = adapter.fromJson(moviesJson) ?: listOf()
            movies
            UseCaseResult.Success(movies)

        } catch (jsonDataException: JsonDataException) {
            UseCaseResult.Error(Failure.ParsingError)
        } catch (exception: Throwable) {
            UseCaseResult.Error(Failure.ServerError)
        }

    }

    private fun getMovieJSON(fileName: String = "popular_movies_list.json"): String {
        val inputStream = getInputStreamForJsonFile(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }

    @Throws(IOException::class)
    internal open fun getInputStreamForJsonFile(fileName: String): InputStream {
        return context.assets.open(fileName)
    }
}
