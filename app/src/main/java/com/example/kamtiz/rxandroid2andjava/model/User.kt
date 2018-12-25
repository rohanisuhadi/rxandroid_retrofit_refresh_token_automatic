package com.example.kamtiz.rxandroid2andjava.model

import com.example.kamtiz.rxandroid2andjava.network.APIClient
import java.util.*

class User {
    var id: Int? = null
    var email: String? = null
    var password: String? = null
    var name: String? = null
    var created_at: Date? = null
    var phone_number:String? = null
    var updated_at: Date? = null
    var location:String? = null
    var gender:String? = null
    var deleted_at: Date? = null
    var birth_date_at: Date? = null
    var token: String? = null
    var photo_url: String? = null
    var oauth_provider: String? = null
    var oauth_uid: String? = null
    var message:String? = null

    fun toJson(): String {
        return APIClient.getGsonInstance().toJson(this)
    }
}