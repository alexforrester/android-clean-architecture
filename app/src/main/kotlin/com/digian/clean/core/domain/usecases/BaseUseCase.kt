package com.digian.clean.core.domain.usecases

import com.digian.clean.core.domain.UseCaseResult
import com.digian.clean.core.domain.exception.Failure

abstract class BaseUseCase<in Params, out Type> where Type : Any {

    abstract fun run(params: Params): UseCaseResult<Failure, Type >

    open operator fun invoke(params: Params): UseCaseResult<Failure, Type> {
        return run(params)
    }

    class None
}