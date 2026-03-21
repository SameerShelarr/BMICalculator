package com.sameershelar.bmicalculator.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BmiDao {
    @Query("SELECT * FROM bmi_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<BmiEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: BmiEntry)

    @Delete
    suspend fun delete(entry: BmiEntry)

    @Query("DELETE FROM bmi_history")
    suspend fun deleteAll()
}
