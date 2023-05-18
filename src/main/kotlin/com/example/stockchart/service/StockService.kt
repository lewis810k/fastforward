package com.example.stockchart.service

import com.example.stockchart.dto.Chart
import com.example.stockchart.dto.FinanceChartResponse
import com.example.stockchart.dto.Quote
import com.example.stockchart.entity.StockSummary
import com.example.stockchart.repository.StockSummaryRepository
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Service
class StockService(val stockSummaryRepository: StockSummaryRepository) {
    fun findStockSummary(symbol: String = "005930.KS", interval: String = "1d", range: String = "5d"): List<StockSummary> {
        val chart = this.getFinanceChart(symbol, interval, range);
        if (chart == null || chart.result.isEmpty()) {
            return arrayListOf()
        }

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val timestamps = chart.result.first().timestamp
        val start = simpleDateFormat.format(timestamps.first() * 1000)
        val end = simpleDateFormat.format(timestamps.last() * 1000)

        val stockSummaries = stockSummaryRepository.findByTransactionDateBetweenAndSymbol(start, end, symbol)

        val meta = chart.result.first().meta
        val quotes = chart.result.first().indicators.quote

        for (i in timestamps.indices) {
            val transactionDate = simpleDateFormat.format(timestamps[i] * 1000)
            val stockSummary = stockSummaries.find { it.symbol == symbol && it.transactionDate == transactionDate }
            val quote = quotes.first()

            if (stockSummary != null) {
                if (stockSummary.timestamp == timestamps[i]) {
                    continue
                }

                stockSummary.close = quote.close[i]
                stockSummary.high = quote.high[i]
                stockSummary.low = quote.low[i]
                stockSummary.open = quote.open[i]
                stockSummary.volume = quote.volume[i]
                stockSummary.timestamp = timestamps[i]
                stockSummary.updatedDate = LocalDateTime.now()
                stockSummaryRepository.save(stockSummary)
            } else {
                val newStockSummary = StockSummary(
                        transactionDate = simpleDateFormat.format(timestamps[i] * 1000),
                        close = quote.close[i],
                        high = quote.high[i],
                        low = quote.low[i],
                        open = quote.open[i],
                        volume = quote.volume[i],
                        timestamp = timestamps[i],
                        symbol = meta.symbol);
                stockSummaryRepository.save(newStockSummary)
            }
        }

        return stockSummaryRepository.findByTransactionDateBetweenAndSymbol(start, end, symbol)
    }

    private fun getFinanceChart(symbol: String, interval: String, range: String): Chart? {
        println(symbol)
        val restTemplate: RestTemplate = RestTemplateBuilder().build()
        val builder: UriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("https://query1.finance.yahoo.com/v8/finance/chart/${symbol}?interval=${interval}&range=${range}")

        return try {
            restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, FinanceChartResponse::class.java).body?.chart
        } catch (err: HttpClientErrorException) {
            println(err)
            null
        }
    }
}