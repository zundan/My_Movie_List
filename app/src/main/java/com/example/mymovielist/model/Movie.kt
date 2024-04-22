package com.example.mymovielist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var id: String? = null,
    var judul: String? = null,
    var genre: String? = null,
    var rating: Double? = null,
    var poster: String? = null,
    var desc: String? = null,
    var director: String? = null,
    var tahunRilis: Int? = null,
) : Parcelable
