package com.willi16.jakal7.dashboard.model

import  android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class LapanganFutsal (
    var fasilitas: String? = "",
    var alamat: String? = "",
    var jamOperasional: String? = "",
    var category: String? = "",
    var desc: String? = "",
    var gambar1: String? = "",
    var gambar2: String? = "",
    var gambar3: String? = "",
    var poster: String? = "",
    var price: Int? = 0,
    var rating: String? = ""

):Parcelable