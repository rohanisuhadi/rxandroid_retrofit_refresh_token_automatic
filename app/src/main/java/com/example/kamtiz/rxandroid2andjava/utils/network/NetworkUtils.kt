package com.example.kamtiz.rxandroid2andjava.utils.network

import android.content.Context
import okhttp3.Cache
import java.io.File

object NetworkUtils {

    fun provideCache(context: Context): Cache?{
        try{
            return Cache(File(context.cacheDir, "http-cache"), 20 * 1024 * 1024 /* 20 MB */)
        }catch(e: Exception){
            return null
        }
    }

}