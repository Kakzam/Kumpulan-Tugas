package com.azhar.banksampah.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.azhar.banksampah.database.DatabaseClient.Companion.getInstance
import com.azhar.banksampah.database.dao.DatabaseDao
import com.azhar.banksampah.model.ModelDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
/**
 * Created by Azhar Rivaldi on 19-11-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

class InputDataViewModel(application: Application) : AndroidViewModel(application) {

    var databaseDao: DatabaseDao?

    fun addDataSampah(
        jenisbarang: String,
        berat: Int,
        harga: Int,
        tanggal: String,
        catatan: String
    ) {
        Completable.fromAction {
            val modelDatabase = ModelDatabase()
            modelDatabase.jenisbarang = jenisbarang
            modelDatabase.berat = berat
            modelDatabase.harga = harga
            modelDatabase.tanggal = tanggal
            modelDatabase.catatan = catatan
            databaseDao?.insertData(modelDatabase)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }



    init {
        databaseDao = getInstance(application)?.appDatabase?.databaseDao()
    }

}