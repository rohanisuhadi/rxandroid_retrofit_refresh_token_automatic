package com.example.kamtiz.rxandroid2andjava.utils.network

import android.content.Context

class TokenAccount(val context: Context) {

    companion object {
        val SHARED_PREFS_NAME = "Account"
        val KEY_TOKEN = "TOKEN"
        private val TAG = "TokenAccount"
    }

    val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    val spEditor = sharedPrefs.edit()

    fun setTokenAccount(token: String) {

        spEditor.putString(KEY_TOKEN, token)
        spEditor.apply()
    }

    fun getTokenAccount() = sharedPrefs.getString(KEY_TOKEN, "")

    fun clearTokenAccount() {

        spEditor.remove(KEY_TOKEN)
        spEditor.apply()
    }

    fun clearUserCache() {
        NetworkUtils.provideCache(context)?.delete()
    }

}