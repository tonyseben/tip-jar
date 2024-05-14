package com.example.tipjar.data.repository

import com.example.tipjar.data.local.database.dao.TipHistoryDao
import com.example.tipjar.data.local.database.entity.TipHistory
import javax.inject.Inject

interface TipRepository {
    suspend fun save(tipHistory: TipHistory): Boolean
    suspend fun updateReceipt(tipHistory: TipHistory): Boolean
    suspend fun getAll(): List<TipHistory>
    suspend fun delete(tipHistory: TipHistory): Boolean
}


class TipRepositoryImpl @Inject constructor(
    private val tipHistoryDao: TipHistoryDao
) : TipRepository {

    override suspend fun save(tipHistory: TipHistory): Boolean {
        return tipHistoryDao.insert(tipHistory) > 0
    }

    override suspend fun updateReceipt(tipHistory: TipHistory): Boolean {
        if (tipHistory.receiptUri.isNullOrEmpty()) {
            throw Exception("Unable to update receipt uri. Receipt uri is null or empty")
        }
        return tipHistoryDao.updateReceiptUri(tipHistory.timestamp, tipHistory.receiptUri) > 0
    }

    override suspend fun getAll(): List<TipHistory> {
        return tipHistoryDao.readAll()
    }

    override suspend fun delete(tipHistory: TipHistory): Boolean {
        return tipHistoryDao.delete(tipHistory.timestamp) > 0
    }

}