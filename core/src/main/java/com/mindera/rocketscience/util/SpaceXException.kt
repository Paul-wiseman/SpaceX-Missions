package com.mindera.rocketscience.util

sealed class SpaceXException(message: String):Exception(message) {
    class NetworkException(message: String) : SpaceXException(message)
    class ApiException(message: String, val statusCode: Int) : SpaceXException(message)
}