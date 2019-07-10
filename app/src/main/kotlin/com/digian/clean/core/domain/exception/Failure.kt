package com.digian.clean.core.domain.exception

import com.squareup.moshi.JsonDataException
import java.lang.Exception


/**
 * Created by Alex Forrester on 2019-05-15.
 *
 * Base Class for handling errors/failures/exceptions.
 *
 * @see <a href="https://github.com/android10/Android-CleanArchitecture-Kotlin/blob/master/app/src/main/kotlin/com/fernandocejas/sample/core/exception/Failure.kt">Fernando Cejas Clean Kotlin Architecture</a>
 *
 */
sealed class Failure(val exception: Exception) {
    //TODO - Add to networking client for retrieval
    class NetworkConnection(exception : Exception) : Failure(exception)
    class ServerError(exception : Exception) : Failure(exception)
    class ParsingError(jsonDataException : JsonDataException) : Failure(jsonDataException)

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(exception : Exception) : Failure(exception)
}