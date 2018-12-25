package com.example.kamtiz.rxandroid2andjava.network

import com.example.kamtiz.rxandroid2andjava.model.AccessToken
import com.example.kamtiz.rxandroid2andjava.model.AudioBook
import com.example.kamtiz.rxandroid2andjava.model.User
import retrofit2.http.*
import java.util.*

interface APIService {

    @POST("user")
    fun registerEmail(@Body model: User): io.reactivex.Observable<User>

    @POST("auth")
    fun loginEmail(@Body user: User): io.reactivex.Observable<User>

    @POST("loginGoogle")
    fun loginGoogle(@Body user: User): io.reactivex.Observable<User>

    @POST("loginFB")
    fun loginFB(@Body user: User): io.reactivex.Observable<User>

    @PUT("auth")
    fun refreshToken(): io.reactivex.Observable<AccessToken>

    /* Audio Book */
    @GET("audiobooks")
    fun getAudioBooks(@QueryMap params: HashMap<String, String>): io.reactivex.Observable<List<AudioBook>>
}