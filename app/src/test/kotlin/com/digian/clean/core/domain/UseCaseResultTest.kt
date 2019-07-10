package com.digian.clean.core.domain

import com.digian.clean.core.domain.exception.Failure
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class UseCaseResultTest {

    @Test
    fun `UseCaseResult Success should return correct type`() {
        val result = UseCaseResult.Success("some example")

        result.successOrError({
            fail()
        }, { success ->
            assertEquals("some example", success)
        })
    }

    @Test
    fun `UseCaseResult Error should return correct type`() {
        val result = UseCaseResult.Error(Failure.NetworkConnection)

        result.successOrError({
            assertEquals(Failure.NetworkConnection, it)
        }, {
            fail()
        })
    }
}