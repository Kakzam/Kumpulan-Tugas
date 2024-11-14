package com.azhar.banksampah.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.azhar.banksampah.database.dao.DatabaseDao
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

@Database(entities = [ModelDatabase::class, ModelDatabaseMasuk::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao?

}
