package com.digian.clean.core.domain.ports

import com.digian.clean.core.data.exception.Failures
import com.digian.clean.core.data.exception.NETWORK_UNAVAILABLE
import com.digian.clean.core.data.exception.NetworkConnectionException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

internal class UseCaseOutputPortTest {

    @Test
    fun `UseCaseOutput Success should return correct type`() {
        val result = UseCaseOutputPort.Success("some example")

        result.successOrError({
            fail()
        }, { success ->
            assertEquals("some example", success)
        })
    }

    @Test
    fun `UseCaseOutput Error should return correct type`() {
        val result = UseCaseOutputPort.Error(
            Failures.NetworkUnavailable(
                NetworkConnectionException(
                    NETWORK_UNAVAILABLE
                )
            )
        )

        result.successOrError({
            assertEquals(NETWORK_UNAVAILABLE, it.exception.message)
        }, {
            fail()
        })
    }
}