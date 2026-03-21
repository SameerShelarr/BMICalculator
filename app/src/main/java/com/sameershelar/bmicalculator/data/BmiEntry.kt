package com.sameershelar.bmicalculator.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bmi_history")
data class BmiEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bmi: Float,
    val weight: Float,
    val height: Int,
    val timestamp: Long = System.currentTimeMillis(),
)
