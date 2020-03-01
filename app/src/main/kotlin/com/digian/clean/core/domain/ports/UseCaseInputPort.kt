package com.digian.clean.core.domain.ports

/**
 * Created by Alex Forrester on 2019-07-12.
 *
 * UserCaseInput ports
*/
sealed class UseCaseInputPort {

    object None
    class Single<Data: Any>(val data: Data)
    class Multiple<Data: Any>(vararg val data: Data)
}

