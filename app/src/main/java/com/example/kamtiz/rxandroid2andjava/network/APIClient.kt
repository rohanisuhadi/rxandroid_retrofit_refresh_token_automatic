package com.example.kamtiz.rxandroid2andjava.network

import android.content.Context
import com.example.kamtiz.rxandroid2andjava.local.FineractInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object APIClient {

    val BASE_URL = "http://192.168.1.4/android/public/api/v1/"

    private var retrofitRefresh: Retrofit? = null
    private val retrofit: Retrofit? = null

    fun create(mContext: Context, externalInterceptor: Interceptor? = null, cache: Cache? = null, forceRefreshCache: Boolean = false):
            APIService {
        val gson = getGsonInstance()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder().addInterceptor(interceptor)
        // Add header "Accept" to accept json only from the server
        httpClient.addInterceptor { chain ->
            chain?.proceed(chain.request()?.newBuilder()?.addHeader("Accept", "application/json")?.build())
        }

        /*val token = TokenAccount(mContext).getTokenAccount()

        if (token != null && !token.isEmpty())
            httpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    return chain?.proceed(chain!!.request()?.newBuilder()?.addHeader("Authorization", "Bearer ${token}")?.build())
                }
            })*/

        httpClient.addInterceptor(FineractInterceptor(mContext))

        // Add external interceptor
        if (externalInterceptor != null)
            httpClient.addInterceptor(externalInterceptor)

        // Add cache network interceptor
        httpClient.addNetworkInterceptor { chain ->
            val response = chain.proceed(chain.request())
            if (forceRefreshCache) {
                val cacheControl = CacheControl.Builder().maxAge(1, TimeUnit.SECONDS).build()
                response.newBuilder().header("Cache-Control", cacheControl.toString()).build()
            } else {
                val cacheControl = CacheControl.Builder().maxAge(2, TimeUnit.MINUTES).build()
                response.newBuilder().header("Cache-Control", cacheControl.toString()).build()
            }
        }

        if (cache != null)
            httpClient.cache(cache)

        val client = httpClient.build()

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(APIService::class.java)
    }

    fun getGsonInstance(): Gson {
        return GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
    }


}