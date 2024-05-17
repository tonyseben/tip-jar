package com.example.tipjar.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tipjar.data.local.database.entity.TipHistory

@Dao
interface TipHistoryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(tipEntry: TipHistory): Long
    // Returns row-id.

    @Query("UPDATE tip_history SET receipt_uri = :receiptUri WHERE timestamp = :timestamp")
    suspend fun updateReceiptUri(timestamp: Long, receiptUri: String): Int
    // Returns number of rows updated.

    @Query("SELECT * FROM tip_history WHERE timestamp == :timestamp")
    suspend fun read(timestamp: Long): TipHistory

    @Query("SELECT * FROM tip_history ORDER BY timestamp DESC")
    suspend fun readAll(): List<TipHistory>

    @Query("DELETE FROM tip_history WHERE timestamp == :timestamp")
    suspend fun delete(timestamp: Long): Int
    // Returns number of rows deleted.
}