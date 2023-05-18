package com.example.stockchart.repository

import com.example.stockchart.entity.StockSummary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StockSummaryRepository: JpaRepository<StockSummary, String> {
    fun findByTransactionDateBetweenAndSymbol(transactionDateStart: String, transactionDateEnd: String, symbol: String): List<StockSummary>
}