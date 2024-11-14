package com.azhar.banksampah.database.dao

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.azhar.banksampah.model.ModelDatabase
import com.azhar.banksampah.model.ModelDatabaseMasuk

/**
 * Created by Azhar Rivaldi on 6-12-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */
@Dao
interface DatabaseDao {

    @Query("SELECT * FROM tbl_pendapatan")
    fun getAllMasuk(): LiveData<List<ModelDatabaseMasuk>>

    @Query("SELECT * FROM tbl_pengeluaran ")
    fun getAll(): LiveData<List<ModelDatabase>>


    @Query("SELECT SUM(harga) FROM tbl_pengeluaran")
    fun getSaldo(): LiveData<Int>

    @Query("SELECT SUM(harga) FROM tbl_pendapatan ")
    fun getSaldoMasuk(): LiveData<Int>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(modelDatabases: ModelDatabase)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDataMasuk(modelDatabases: ModelDatabaseMasuk)


    @Query("DELETE FROM tbl_pendapatan WHERE uid= :uid")
    fun deleteSingleDataMasuk(uid: Int)

    @Query("DELETE FROM tbl_pengeluaran WHERE uid= :uid")
    fun deleteSingleData(uid: Int)

}