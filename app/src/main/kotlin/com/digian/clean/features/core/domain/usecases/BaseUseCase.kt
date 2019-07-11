package com.digian.clean.features.core.domain.usecases

import com.digian.clean.features.core.domain.UseCaseResult
import com.digian.clean.features.core.data.exception.Failures
import com.digian.clean.features.core.domain.exception.Failure

/**
 * Created by Alex Forrester on 2019-05-15.
 *
 * use case handling
 */
abstract class BaseUseCase<in Params, out Type> where Type : Any {

    abstract fun run(params: Params): UseCaseResult<Failure, Type>

    open operator fun invoke(params: Params): UseCaseResult<Failure, Type> {
        return run(params)
    }

    /**
     * Marker class to denote not passing any params into use case
     */
    class None
}