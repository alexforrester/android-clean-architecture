package com.digian.sample.clean.core.domain


/**
 * Created by Alex Forrester on 26/06/2019.
 *
 * Credits to Fernando Cejas: https://fernandocejas.com/2018/05/07/architecting-android-reloaded/
 *
 * @see <a href="https://github.com/android10/Android-CleanArchitecture-Kotlin/blob/master/app/src/main/kotlin/com/fernandocejas/sample/core/functional/Either.kt">'Either' class and convention for use</a>
 *
 **/
sealed class UseCaseResult<out Failure, out Type> {

    /** Represents modified left side of [Either] type class which by convention is a "Failure". */
    class Error<out Failure>(val failure: Failure) : UseCaseResult<Failure, Nothing>()
    /** Represents modified right side of [Either] type class which by convention is a "Success". */
    class Success<out T>(val data: T) : UseCaseResult<Nothing, T>()

    fun successOrError(functionFailure: (Failure) -> Any, functionType: (Type) -> Any): Any =
        when (this) {
            is Error -> functionFailure(failure)
            is Success -> functionType(data)
        }
}