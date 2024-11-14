package com.azhar.banksampah.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * Created by Azhar Rivaldi on 6-12-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

@Entity(tableName = "tbl_pengeluaran")
class ModelDatabase : Serializable {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    @ColumnInfo(name = "jenis_barang")
    lateinit var jenisbarang: String

    @ColumnInfo(name = "berat")
    var berat: Int = 0

    @ColumnInfo(name = "harga")
    var harga: Int = 0

    @ColumnInfo(name = "tanggal")
    lateinit var tanggal: String

    @ColumnInfo(name = "catatan")
    lateinit var catatan: String

}
