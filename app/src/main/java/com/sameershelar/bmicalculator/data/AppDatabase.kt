package com.sameershelar.bmicalculator.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BmiEntry::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bmiDao(): BmiDao
}
