package com.example.tipjar.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.tipjar.data.local.database.TipDatabase
import com.example.tipjar.data.local.database.dao.TipHistoryDao
import com.example.tipjar.data.local.database.entity.TipHistory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TipRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TipDatabase
    private lateinit var dao: TipHistoryDao
    private lateinit var repository: TipRepository

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TipDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.tipHistoryDao()
        repository = TipRepositoryImpl(dao)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun saveTipHistory_retrievesTipHistory() = runTest {
        // Given
        val tipHistory = TipHistory(1000, "USD", 200.0, 2, 10, null)

        // When
        repository.save(tipHistory)

        // Then
        val retrievedTipHistory = repository.getAll().first()
        assertEquals(tipHistory, retrievedTipHistory)
    }

    @Test
    fun updateTipHistory_retrievesReceiptUri() = runTest {
        // Given
        val tipHistory = TipHistory(1000, "USD", 200.0, 2, 10, null)
        val timestamp = 1000L
        val receiptUri = "https://example.com/receipt.pdf"

        // When
        repository.save(tipHistory)
        repository.updateReceipt(timestamp, receiptUri)

        // Then
        val retrievedTipHistory = repository.getAll().first()
        assertEquals(receiptUri, retrievedTipHistory.receiptUri)
    }

    @Test
    fun deleteTipHistory_deletesTipHistory() = runTest {
        // Given
        val tipHistory = TipHistory(1000, "USD", 200.0, 2, 10, null)
        repository.save(tipHistory)

        // When
        repository.delete(tipHistory)

        // Then
        val retrievedTipHistory = repository.getAll()
        assertTrue(retrievedTipHistory.isEmpty())
    }
}