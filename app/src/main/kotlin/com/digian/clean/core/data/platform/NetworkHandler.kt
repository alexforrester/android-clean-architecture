package com.digian.clean.core.data.platform

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by Alex Forrester on 2019-05-15.
 */
class NetworkHandler (private val context: Context) {
    val isConnected: Boolean
        get() {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }
}
