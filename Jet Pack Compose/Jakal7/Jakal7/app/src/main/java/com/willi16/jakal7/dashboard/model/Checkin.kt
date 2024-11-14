package com.willi16.jakal7.dashboard.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Checkin (
    var nama: String? = "",
    var idUser: String? = "",
    var emailUser: String? = "",
    var photoUser: String? = "",
    var poster: String? = "",
    var statusUser: String? = "",
    var lapanganUser: String? = "",
    var sewaUser: Int? = null,
    var phoneUser: String? = "",
    var alamatUser: String? = "",
    var priceUser: Int? = null,
    var fasilitas: String? = "",
    var pending: String? = "",
    var tanggal: String? = ""

): Parcelable