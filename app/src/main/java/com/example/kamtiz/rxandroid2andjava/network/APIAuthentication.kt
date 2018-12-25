package com.example.kamtiz.rxandroid2andjava.network

import android.content.Context
import com.example.kamtiz.rxandroid2andjava.model.AccessToken
import com.example.kamtiz.rxandroid2andjava.model.AudioBook
import com.example.kamtiz.rxandroid2andjava.model.User
import java.util.*

class APIAuthentication(mContext :Context){

    private val api = APIClient.create(mContext)

    fun registerEmail(user: User): io.reactivex.Observable<User> {
        return  api.registerEmail(user)
    }

    fun loginEmail(user: User): io.reactivex.Observable<User> {
        return  api.loginEmail(user)
    }

    fun loginGoogle(user: User): io.reactivex.Observable<User> {
        return  api.loginGoogle(user)
    }

    fun loginFB(user: User): io.reactivex.Observable<User> {
        return  api.loginFB(user)
    }

    fun refreshToken(): io.reactivex.Observable<AccessToken> {
        return  api.refreshToken()
    }

    fun getAudioBook(params: Map<String, String>): io.reactivex.Observable<List<AudioBook>> {
        return  api.getAudioBooks(params as HashMap<String, String>)
    }

}