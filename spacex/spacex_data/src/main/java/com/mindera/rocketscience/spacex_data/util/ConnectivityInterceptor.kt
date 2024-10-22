package com.mindera.rocketscience.spacex_data.util

import android.content.Context
import com.mindera.rocketscience.spacex_data.util.Constants.NO_INTERNET_CONNECTION
import com.mindera.rocketscience.util.NetworkUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(
     private val context: Context,
    private val netWorkUtil: NetworkUtil
) :Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!netWorkUtil.isInternetAvailable(context)) {
            throw IOException(NO_INTERNET_CONNECTION)
        } else {
            return chain.proceed(chain.request())
        }
    }
}