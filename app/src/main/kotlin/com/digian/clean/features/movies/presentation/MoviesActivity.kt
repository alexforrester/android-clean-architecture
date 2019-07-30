package com.digian.clean.features.movies.presentation

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.IdlingResource
import com.digian.clean.R
import com.digian.clean.features.movies.presentation.IdlingResource.SimpleIdlingResource


/**
 * Created by Alex Forrester on 18/04/2019.
 */
class MoviesActivity : AppCompatActivity() {

    // The Idling Resource which will be null in production
    private var idlingResource : SimpleIdlingResource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        title = getString(R.string.popular_movies)
    }

    @VisibleForTesting
    fun getIdlingResource(): IdlingResource {
        if (idlingResource == null) {
            idlingResource =
                SimpleIdlingResource()
        }
        return idlingResource as SimpleIdlingResource
    }
}
