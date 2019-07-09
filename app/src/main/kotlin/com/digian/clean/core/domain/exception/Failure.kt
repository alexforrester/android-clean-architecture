package com.digian.clean.core.domain.exception


/**
 * Created by Alex Forrester on 2019-05-15.
 *
 * Base Class for handling errors/failures/exceptions.
 *
 * @see <a href="https://github.com/android10/Android-CleanArchitecture-Kotlin/blob/master/app/src/main/kotlin/com/fernandocejas/sample/core/exception/Failure.kt">Fernando Cejas Clean Kotlin Architecture</a>
 *
 */
sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object ParsingError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}