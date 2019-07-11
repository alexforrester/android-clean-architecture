package com.digian.clean.features.core.data.platform

import android.content.Context
import android.net.NetworkInfo
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

/**
 * Created by Alex Forrester on 2019-06-06.
 */
internal class NetworkHandlerTest {

    private val context = mockk<Context>()
    private val connectivityManager = mockk<android.net.ConnectivityManager>()
    private val networkInfo = mockk<NetworkInfo>()

    @Test
    fun `given testing network connectivity, when network info is null, then isConnected return false`() {

        every {context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every {connectivityManager.activeNetworkInfo } returns null

        val networkHandler = NetworkHandler(context)

        assertEquals(false, networkHandler.isConnected)
    }

    @Test
    fun `given testing network connectivity, when no network info, then isConnected return false`() {

        every {context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every {networkInfo.isConnected } returns false
        every {connectivityManager.activeNetworkInfo } returns networkInfo

        val networkHandler = NetworkHandler(context)

        assertEquals(false, networkHandler.isConnected)
    }

    @Test
    fun `given testing network connectivity, when network info, then isConnected return true`() {

        every {context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every {networkInfo.isConnected } returns true
        every {connectivityManager.activeNetworkInfo } returns networkInfo

        val networkHandler = NetworkHandler(context)

        assertEquals(true, networkHandler.isConnected)
    }
}