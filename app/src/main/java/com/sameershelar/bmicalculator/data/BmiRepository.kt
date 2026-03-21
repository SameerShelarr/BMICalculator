package com.sameershelar.bmicalculator.data

import kotlinx.coroutines.flow.Flow

class BmiRepository(
    private val bmiDao: BmiDao,
) {
    fun getBmiHistory(): Flow<List<BmiEntry>> = bmiDao.getAllHistory()

    suspend fun insertBmiEntry(entry: BmiEntry) = bmiDao.insert(entry)

    suspend fun deleteBmiEntry(entry: BmiEntry) = bmiDao.delete(entry)

    suspend fun deleteAllHistory() = bmiDao.deleteAll()
}
