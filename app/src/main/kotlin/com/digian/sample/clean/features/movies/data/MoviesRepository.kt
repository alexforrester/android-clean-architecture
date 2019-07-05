package com.digian.sample.clean.features.movies.data

import android.content.Context
import com.digian.sample.clean.core.domain.UseCaseResult
import com.digian.sample.clean.core.domain.exception.Failure
import com.digian.sample.clean.features.movies.data.entities.MovieData
import com.digian.sample.clean.features.movies.data.mappers.MovieDataEntityMapper
import com.digian.sample.clean.features.movies.domain.PopularMoviesRepository
import com.digian.sample.clean.features.movies.domain.entities.MovieEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import java.io.InputStream


internal object MoshiFactory {

    private val moshi: Moshi = Moshi.Builder()
        .add(GenreAdapter())
        .build()

    fun getInstance() = moshi
}

internal open class PopularMoviesRepositoryImpl(
    private val context: Context,
    private val moshi: Moshi = MoshiFactory.getInstance()
) : PopularMoviesRepository {

    override fun getMovies(): UseCaseResult<Failure, List<MovieEntity>> {

        return try {

            val moviesJson = getMovieJSON()

            val listType = Types.newParameterizedType(List::class.java, MovieData::class.java)
            val adapter: JsonAdapter<List<MovieData>> = moshi.adapter(listType)

            val moviesData = adapter.fromJson(moviesJson) ?: listOf()

            val moviesEntity = moviesData.flatMap {
                listOf(MovieDataEntityMapper.mapFrom(it))
            }

            UseCaseResult.Success(moviesEntity)

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
