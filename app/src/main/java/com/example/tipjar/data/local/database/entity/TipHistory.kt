package com.example.tipjar.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "tip_history", primaryKeys = ["timestamp"])
data class TipHistory(
    @ColumnInfo(name = "timestamp") val timestamp: Long

)