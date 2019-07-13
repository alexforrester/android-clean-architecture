package com.digian.clean.features.core.data

import com.digian.clean.features.core.data.exception.Failures
import com.digian.clean.features.core.data.exception.NETWORK_UNAVAILABLE
import com.digian.clean.features.core.data.exception.NetworkConnectionException
import com.digian.clean.features.core.domain.ports.UseCaseOutput
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class UseCaseOutputTest {

    @Test
    fun `UseCaseResult Success should return correct type`() {
        val result = UseCaseOutput.Success("some example")

        result.successOrError({
            fail()
        }, { success ->
            assertEquals("some example", success)
        })
    }

    @Test
    fun `UseCaseResult Error should return correct type`() {
        val result = UseCaseOutput.Error(
            Failures.NetworkUnavailable(
                NetworkConnectionException(
                    NETWORK_UNAVAILABLE
                )
            )
        )

        result.successOrError({
            assertTrue(it is Failures.NetworkUnavailable)
            assertEquals(NETWORK_UNAVAILABLE, it.exception.message)
        }, {
            fail()
        })
    }
}