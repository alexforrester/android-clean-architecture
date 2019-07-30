package com.digian.clean.core.domain.usecases

/**
 * Created by Alex Forrester on 2019-07-12.
 *
 * UserCaseInput ports
*/
sealed class UseCaseInput {

    object None
    class Single<Data: Any>(val data: Data)
    class Multiple<Data: Any>(vararg val data: Data)
}

