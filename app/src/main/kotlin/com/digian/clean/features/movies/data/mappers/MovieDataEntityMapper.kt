package com.digian.clean.features.movies.data.mappers

import com.digian.clean.core.data.Mapper
import com.digian.clean.features.movies.data.MovieData
import com.digian.clean.features.movies.domain.entities.GenreEntity
import com.digian.clean.features.movies.domain.entities.MovieEntity

object MovieDataEntityMapper : Mapper<MovieData, MovieEntity> {
    override fun mapFrom(from: MovieData): MovieEntity {

        val genres: List<GenreEntity> = from.genresData.flatMap {
            listOf(GenreEntity(it.id, it.name))
        }

        return MovieEntity(
            from.id,
            from.voteCount,
            from.title,
            from.imagePath,
            genres,
            from.overview
        )
    }
}