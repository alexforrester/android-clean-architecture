package com.digian.clean.core.domain

import com.digian.clean.core.domain.exception.Failure
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.lang.Exception

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
        val result = UseCaseResult.Error(Failure.NetworkConnection(Exception("some message")))

        result.successOrError({
            assertTrue(it is Failure.NetworkConnection)
            assertEquals("some message", it.exception.message)
        }, {
            fail()
        })
    }
}