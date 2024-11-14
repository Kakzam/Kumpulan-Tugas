package com.azhar.banksampah.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tbl_pendapatan")
class ModelDatabaseMasuk : Serializable {
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