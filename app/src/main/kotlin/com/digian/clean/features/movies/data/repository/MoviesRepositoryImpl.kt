package com.digian.clean.features.movies.data.repository

import android.content.Context
import com.digian.clean.core.data.exception.Failures
import com.digian.clean.core.data.exception.NETWORK_UNAVAILABLE
import com.digian.clean.core.data.exception.NetworkConnectionException
import com.digian.clean.core.data.platform.NetworkHandler
import com.digian.clean.core.domain.exception.Failure
import com.digian.clean.core.domain.ports.UseCaseInputPort
import com.digian.clean.core.domain.ports.UseCaseOutputPort
import com.digian.clean.features.movies.data.MovieData
import com.digian.clean.features.movies.data.exception.MovieCollectionException
import com.digian.clean.features.movies.data.mappers.MovieDataEntityMapper
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.repository.MoviesRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import java.io.InputStream

/**
 * Created by Alex Forrester on 23/04/2019.
 *
 * Gets movie data for list and detail display
 *
 * Currently only implemented as a flat JSON file with no datasources, caching and remote retrieval
 *
 */
open class MoviesRepositoryImpl(
    private val context: Context,
    private val moshi: Moshi,
    private val networkHandler: NetworkHandler
) : MoviesRepository {

    override suspend fun getMovieDetail(movieIdInputPort: UseCaseInputPort.Single<Int>): UseCaseOutputPort<Failure, MovieEntity> {

        if (!networkHandler.isConnected) {
            return UseCaseOutputPort.Error(Failures.NetworkUnavailable(NetworkConnectionException(NETWORK_UNAVAILABLE)))
        }

        val movieId = movieIdInputPort.data
        val movies = getMovieEntities()

        return try {

            UseCaseOutputPort.Success(movies.single {
                it.id == movieId
            })

        } catch (iae: IllegalArgumentException) {
            UseCaseOutputPort.Error(MovieCollectionException(iae))
        } catch (nse: NoSuchElementException) {
            UseCaseOutputPort.Error(MovieCollectionException(nse))
        } catch (exception: Exception) {
            UseCaseOutputPort.Error(Failures.ServerException(exception))
        }
    }

    override suspend fun getMovies(none: UseCaseInputPort.None): UseCaseOutputPort<Failure, List<MovieEntity>> {

        if (!networkHandler.isConnected) {
            return UseCaseOutputPort.Error(Failures.NetworkUnavailable(NetworkConnectionException(NETWORK_UNAVAILABLE)))
        }

        return try {

            val moviesEntities = getMovieEntities()
            UseCaseOutputPort.Success(moviesEntities)

        } catch (jsonDataException: JsonDataException) {
            UseCaseOutputPort.Error(Failures.ParsingException(jsonDataException))
        } catch (exception: Exception) {
            UseCaseOutputPort.Error(Failures.ServerException(exception))
        }

    }

    private suspend fun getMovieEntities(): List<MovieEntity> {
        val moviesJson = getMovieJSON()

        val listType = Types.newParameterizedType(List::class.java, MovieData::class.java)
        val adapter: JsonAdapter<List<MovieData>> = moshi.adapter(listType)

        val moviesData = adapter.fromJson(moviesJson) ?: listOf()

        val moviesEntities = moviesData.flatMap {
            listOf(MovieDataEntityMapper.mapFrom(it))
        }
        return moviesEntities
    }

    private suspend fun getMovieJSON(fileName: String = "popular_movies_list.json"): String {
        val inputStream = getInputStreamForJsonFile(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }

    @Throws(IOException::class)
    internal open suspend fun getInputStreamForJsonFile(fileName: String): InputStream {
        return context.assets.open(fileName)
    }
}
