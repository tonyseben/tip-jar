package com.example.tipjar.data.local.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.tipjar.data.local.database.TipDatabase
import com.example.tipjar.data.local.database.entity.TipHistory
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class TipHistoryDaoTest {

    private lateinit var database: TipDatabase
    private lateinit var tipHistoryDao: TipHistoryDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TipDatabase::class.java
        ).allowMainThreadQueries().build()

        tipHistoryDao = database.tipHistoryDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveTipHistory() = runTest {

        // Given
        val tipHistory = TipHistory(1000, "$", 200.0, 2, 10, null)
        // When
        tipHistoryDao.insert(tipHistory)
        // Then
        assert(tipHistoryDao.readAll().isNotEmpty())

    }

    @Test
    fun updateTipHistory() = runTest {

        // Given
        val tipHistory = TipHistory(1000, "$", 200.0, 2, 10, null)
        // When
        tipHistoryDao.insert(tipHistory)
        tipHistoryDao.updateReceiptUri(1000, "test-uri")
        // Then
        assert(tipHistoryDao.readAll().first().receiptUri == "test-uri")
    }

    @Test
    fun deleteTipHistory() = runTest {

        // Given
        val tipHistory = TipHistory(1000, "$", 200.0, 2, 10, null)
        // When
        tipHistoryDao.insert(tipHistory)
        tipHistoryDao.delete(1000)
        // Then
        assert(tipHistoryDao.readAll().isEmpty())
    }
}