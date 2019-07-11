package com.digian.clean.features.core.data.exception

/**
 * Created by Alex Forrester on 2019-07-11.
 *
 * Exception class for No Network
 *
 */
const val NETWORK_UNAVAILABLE = "Network currently unavailable"
class NetworkConnectionException(message: String): Exception(message)