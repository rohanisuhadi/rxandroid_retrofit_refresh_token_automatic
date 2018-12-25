package com.example.kamtiz.rxandroid2andjava.model

import com.google.gson.annotations.SerializedName
import java.util.*

class AudioBook {

    var id: Int? = null
    @SerializedName("category_id")
    var categoryId: Int? = null
    var title: String? = null
    var subtitle: String? = null
    var author: String? = null
    var publisher: String? = null
    var narrator: String? = null
    var isbn: String? = null
    var price: Double? = null
    var about: String? = null
    var audio_preview_file_url: String? = null
    var duration_seconds: Int? = null
    var duration_second_preview: Long? = null
    var cover_picture_url: String? = null
    var banner_picture_url: String? = null
    var copyright_year: String? = null
    var visibility: Int? = null
    var released_at: Date? = null
    var created_at: Date? = null
    var updated_at: Date? = null
    var reviews_count: Int? = null
    var purchase_count: Int? = null
    var ratings_average: Double? = null
    var wishlisted: Boolean? = false
    var purchased: Boolean? = false
    var downloaded: Boolean = false
    var loading:String ?= null
}