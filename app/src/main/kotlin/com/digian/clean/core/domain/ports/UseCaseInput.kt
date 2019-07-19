package com.digian.clean.core.domain.ports

/**
 * Created by Alex Forrester on 2019-07-12.
 *
 * UserCaseInput ports
*/
sealed class UseCaseInput {

    object None
    class Single<Param : Any>(val param: Param)
}
