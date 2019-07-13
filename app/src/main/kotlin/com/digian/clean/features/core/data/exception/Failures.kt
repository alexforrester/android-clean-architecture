package com.digian.clean.features.core.data.exception

import com.digian.clean.features.core.domain.exception.Failure
import com.squareup.moshi.JsonDataException
import java.lang.Exception


/**
 * Created by Alex Forrester on 2019-05-15.
 *
 * Class for handling errors/failure/exceptions
 *
 * Inspired by Class below
 *
 * @see <a href="https://github.com/android10/Android-CleanArchitecture-Kotlin/blob/master/app/src/main/kotlin/com/fernandocejas/sample/core/exception/Failures.ktt">Fernando Cejas Clean Kotlin Architecture</a>
 *
 */
sealed class Failures(val exception: Exception) : Failure {
    class NetworkUnavailable(networkConnectionException: NetworkConnectionException) : Failures(networkConnectionException)
    class ServerException(exception : Exception) : Failures(exception)
    class ParsingException(jsonDataException : JsonDataException) : Failures(jsonDataException)

    /** * Extend this class for feature specific failure.*/
    abstract class FeatureFailure(exception : Exception) : Failures(exception)
}