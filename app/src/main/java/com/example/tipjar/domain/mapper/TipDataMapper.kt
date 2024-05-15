package com.example.tipjar.domain.mapper

import com.example.tipjar.data.local.database.entity.TipHistory
import com.example.tipjar.domain.model.TipData

fun TipData.toTipHistory(): TipHistory = TipHistory(
    timestamp = timestamp,
    currency = currency,
    amount = amount,
    pax = pax,
    tipPercent = tipPercent,
    receiptUri = receiptUri
)

fun TipHistory.toTipData(): TipData = TipData(
    timestamp = timestamp,
    currency = currency,
    amount = amount,
    pax = pax,
    tipPercent = tipPercent,
    receiptUri = receiptUri
)
