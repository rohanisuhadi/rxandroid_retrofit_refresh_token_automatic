package com.example.kamtiz.rxandroid2andjava.utils.network

import android.content.Context
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class OfflineCacheInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response? {
        val request = chain?.request()
        if (!ConnectivityUtils.isConnected(context)) {
            val cacheControl = CacheControl.Builder().maxStale(7, TimeUnit.DAYS).build()
            return chain?.proceed(request?.newBuilder()?.cacheControl(cacheControl)?.build())
        }
        return chain?.proceed(request)
    }
}