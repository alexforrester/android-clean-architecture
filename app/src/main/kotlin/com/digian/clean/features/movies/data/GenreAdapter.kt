package com.digian.clean.features.movies.data

import com.digian.clean.features.movies.data.entities.GenreData
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.ToJson

/**
 * Created by Alex Forrester on 2019-04-26.
 *
 * MovieData genresData from the The movie database - https://www.themoviedb.org/
*/
class GenreAdapter  {

        @ToJson
        fun toJson(genreData: List<GenreData>): List<Int> {
            return genreData.map { genre -> genre.id}
        }

        @FromJson
        fun fromJson(genreId: Int): GenreData {

            when (genreId) {
                28 -> return GenreData(28, "Action")
                12 -> return GenreData(12, "Adventure")
                16 -> return GenreData(16, "Animation")
                35 -> return GenreData(35, "Comedy")
                80 -> return GenreData(80, "Crime")
                99 -> return GenreData(99, "Documentary")
                18 -> return GenreData(18, "Drama")
                10751 -> return GenreData(10751, "Family")
                14 -> return GenreData(14, "Fantasy")
                36 -> return GenreData(36, "History")
                27 -> return GenreData(27, "Horror")
                10402 -> return GenreData(10402, "Music")
                10749 -> return GenreData(10749, "Romance")
                9648 -> return GenreData(9648, "Mystery")
                878 -> return GenreData(878, "Science Fiction")
                10770 -> return GenreData(10770, "TV MovieData")
                53 -> return GenreData(53, "Mystery")
                10752 -> return GenreData(10752, "War")
                37 -> return GenreData(37, "Western")
                else -> throw JsonDataException("unknown genre id: $genreId")
            }
        }
    }