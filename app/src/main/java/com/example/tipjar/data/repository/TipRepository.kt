package com.example.tipjar.data.repository

import com.example.tipjar.data.local.database.dao.TipHistoryDao
import com.example.tipjar.data.local.database.entity.TipHistory
import javax.inject.Inject

interface TipRepository {
    suspend fun save(tipHistory: TipHistory): Boolean
    suspend fun updateReceipt(timestamp: Long, receiptUri: String): Boolean
    suspend fun get(timestamp: Long): TipHistory
    suspend fun getAll(): List<TipHistory>
    suspend fun delete(timestamp: Long): Boolean
}


class TipRepositoryImpl @Inject constructor(
    private val tipHistoryDao: TipHistoryDao
) : TipRepository {

    override suspend fun save(tipHistory: TipHistory): Boolean {
        return tipHistoryDao.insert(tipHistory) > 0
    }

    override suspend fun updateReceipt(timestamp: Long, receiptUri: String): Boolean {
        return tipHistoryDao.updateReceiptUri(timestamp, receiptUri) > 0
    }

    override suspend fun get(timestamp: Long): TipHistory {
        return tipHistoryDao.read(timestamp)
    }

    override suspend fun getAll(): List<TipHistory> {
        return tipHistoryDao.readAll()
    }

    override suspend fun delete(timestamp: Long): Boolean {
        return tipHistoryDao.delete(timestamp) > 0
    }

}