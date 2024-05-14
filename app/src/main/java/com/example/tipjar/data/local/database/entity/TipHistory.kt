package com.example.tipjar.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "tip_history", primaryKeys = ["timestamp"])
data class TipHistory(
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "pax") val pax: Int,
    @ColumnInfo(name = "tip_percent") val tipPercent: Int,
    @ColumnInfo(name = "receipt_uri") val receiptUri: String?
)