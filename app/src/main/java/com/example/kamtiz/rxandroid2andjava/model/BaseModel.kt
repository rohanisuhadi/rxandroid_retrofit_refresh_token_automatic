package com.example.kamtiz.rxandroid2andjava.model

class BaseModel<T> {

    var userMessage: String? = null
    var devMessage: String? = null
    var error: String? = null
    var data: T? = null
}