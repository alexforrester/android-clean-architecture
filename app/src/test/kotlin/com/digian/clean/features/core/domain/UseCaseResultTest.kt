package com.digian.clean.features.core.domain

import com.digian.clean.features.core.domain.exception.Failure
import com.digian.clean.features.core.domain.exception.NETWORK_UNAVAILABLE
import com.digian.clean.features.core.domain.exception.NetworkConnectionException
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
        val result = UseCaseResult.Error(Failure.NetworkConnection(NetworkConnectionException(
            NETWORK_UNAVAILABLE)))

        result.successOrError({
            assertTrue(it is Failure.NetworkConnection)
            assertEquals(NETWORK_UNAVAILABLE, it.exception.message)
        }, {
            fail()
        })
    }
}