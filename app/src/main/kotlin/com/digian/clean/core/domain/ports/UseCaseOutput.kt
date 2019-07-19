package com.digian.clean.core.domain.ports

import com.digian.clean.core.domain.exception.Failure


/**
 * Created by Alex Forrester on 26/06/2019.
 *
 * Credits to Fernando Cejas: https://fernandocejas.com/2018/05/07/architecting-android-reloaded/
 *
 * This class is based on Either.kt customised for Success or Failures of Use Case operation
 *
 * @see <a href="https://github.com/android10/Android-CleanArchitecture-Kotlin/blob/master/app/src/main/kotlin/com/fernandocejas/sample/core/functional/Either.kt">'Either' class and convention for use</a>
 *
 * It is used as the Use Case output port
 **/
sealed class UseCaseOutput<out FailureType: Failure, out Type> {

    /** Represents modified left side of [Either] type class which by convention is a "Failures". */
    class Error<out FailureType : Failure>(val failure: FailureType) : UseCaseOutput<FailureType, Nothing>()
    /** Represents modified right side of [Either] type class which by convention is a "Success". */
    class Success<out Type>(val data: Type) : UseCaseOutput<Nothing, Type>()

    fun successOrError(functionFailure: (FailureType) -> Any, functionType: (Type) -> Any): Any =
        when (this) {
            is Error -> functionFailure(failure)
            is Success -> functionType(data)
        }
}