package com.digian.clean.core.domain.ports

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Created by Alex Forrester on 2019-07-30.
 */
internal class UseCaseInputPortTest {

    @Test
    fun `Given UseCaseInput Single When set, then property available `() {

        val useCaseInput = UseCaseInputPort.Single(3)
        assertEquals(3, useCaseInput.data)
    }

    @Test
    fun `Given UseCaseInput Multiple When set, then property available as array`() {

        val useCaseInput = UseCaseInputPort.Multiple(3, 4, 5)
        var result = 0

        for (i in useCaseInput.data) {
            result += i
        }

        assertEquals(12, result)
    }

}