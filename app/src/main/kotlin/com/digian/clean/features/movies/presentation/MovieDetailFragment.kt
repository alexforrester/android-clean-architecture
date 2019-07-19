package com.digian.clean.features.movies.presentation


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.digian.clean.R
import com.digian.clean.core.data.exception.Failures
import com.digian.clean.features.movies.domain.entities.GenreEntity
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

const val UNKNOWN_MOVIE_ID = 0
const val IMAGE_URL_AND_PATH = "https://image.tmdb.org/t/p/w400"
const val PICASSO_RESULT = "PICASSO_RESULT"

/**
 * Created by Alex Forrester on 23/04/2019.
 *
 * Fragment for displaying movie detail
 */
class MovieDetailFragment : Fragment() {

    companion object {
        fun createGenreText(genreData: List<GenreEntity>): String {
            val genreNames = genreData.map { genre -> genre.name }

            if (genreData.isEmpty()) {
                return ""
            }

            var genresText = "GENRES: "

            genreNames.forEach { genre ->
                genresText += genre.plus(", ")
            }

            return genresText.trimEnd().substringBeforeLast(",")
        }
    }

    private val movieDetailViewModel : MovieDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val movieId: Int = arguments?.getInt("movieId") ?: UNKNOWN_MOVIE_ID

        //Loads movie detail and returns from observer or displays error view
        movieDetailViewModel.movie.observe(this,
            Observer<MovieEntity> { movie ->

                movie?.let { movieDetail ->
                    movieDetail.genres.let { genres ->

                        if (genres.isNotEmpty()) {
                            movie_genres.visibility = View.VISIBLE
                            movie_genres.text =
                                createGenreText(
                                    genres
                                )
                        }
                    }
                    movie_title.text = movieDetail.title
                    movie_description.text = movieDetail.overview
                    movie_votes.text =
                        if (movieDetail.voteCount != -1) "VOTES: ${movieDetail.voteCount}" else ""
                    loadImageView(movieDetail.imagePath)
                    return@Observer
                }

            })

        movieDetailViewModel.failure.observe(this,
            Observer { failure ->
                addErrorView(failure as? Failures)
            })

        movieDetailViewModel.loadMovie(movieId)
    }

    private fun addErrorView(failureException: Failures?) {

        val errorTextView = TextView(activity)
        errorTextView.text = getString(R.string.movie_detail_loading_error).plus(failureException?.exception?.message ?: "")
        errorTextView.gravity = Gravity.CENTER
        errorTextView.textSize = 20f
        errorTextView.layoutParams =
            ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            );
        movie_detail_root.addView(errorTextView)
    }

    private fun loadImageView(posterPath: String?) {

        val uri: Uri = Uri.parse(IMAGE_URL_AND_PATH.plus(posterPath))

        val picasso = Picasso.get()
        picasso.isLoggingEnabled = true

        picasso
            .load(uri)
            .error(R.drawable.ic_error_black_80dp)
            .placeholder(R.drawable.placeholder460_690)
            .into(movie_image, object : Callback {
                override fun onSuccess() {
                    Log.d(PICASSO_RESULT, "onSuccess")
                }

                override fun onError(e: Exception?) {
                    Log.e(PICASSO_RESULT, "onError", e)
                }
            })
    }
}