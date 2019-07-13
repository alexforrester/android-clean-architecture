package com.digian.clean.features.movies.data.exception

import com.digian.clean.features.core.data.exception.Failures

/**
 * Created by Alex Forrester on 2019-07-12.
 */
class MovieCollectionException(exception: Exception) : Failures.FeatureFailure(exception)