package com.example.stockchart.dto

data class FinanceChartResponse(
        val chart: Chart
)

data class Chart(
        val result: List<ChartResult>,
        val error: String?
)

data class ChartResult(
        val meta: ChartMeta,
        val timestamp: List<Long>,
        val indicators: Indicator,
)

data class ChartMeta(
        val currency: String,
        val symbol: String,
        val exchangeName: String,
        val instrumentType: String,
        val firstTradeDate: Long,
        val regularMarketTime: Long,
        val gmtoffset: Int,
        val timezone: String,
        val exchangeTimezoneName: String,
        val regularMarketPrice: Int,
        val chartPreviousClose: Int,
        val priceHint: Int,
        val currentTradingPeriod: CurrentTradingPeriod,
        val dataGranularity: String,
        val range: String,
        val validRanges: List<String>,
)

data class CurrentTradingPeriod(
        val pre: TradingPeriod,
        val regular: TradingPeriod,
        val post: TradingPeriod
)

data class TradingPeriod(
        val timezone: String,
        val start: Long,
        val end: Long,
        val gmtoffset: Int,
)

data class Indicator(
        val quote: List<Quote>,
        val adjclose: List<Adjclose>,
)

data class Quote(
        val open: List<Int>,
        val close: List<Int>,
        val volume: List<Long>,
        val low: List<Int>,
        val high: List<Int>
)

data class Adjclose(
        val adjclose: List<Int>,
)