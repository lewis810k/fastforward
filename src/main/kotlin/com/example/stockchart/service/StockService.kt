package com.example.stockchart.service

import com.example.stockchart.dto.FinanceChartResponse
import com.example.stockchart.dto.Quote
import com.example.stockchart.entity.StockSummary
import com.example.stockchart.repository.StockSummaryRepository
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

@Service
class StockService(
        val stockSummaryRepository: StockSummaryRepository
) {

    fun getCharts(symbol: String = "005930.KS", interval: String = "1d", range: String = "5d"): List<StockSummary> {
        val restTemplate: RestTemplate = RestTemplateBuilder().build()
        val builder: UriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("https://query1.finance.yahoo.com/v8/finance/chart/${symbol}?interval=${interval}&range=${range}")

        val body: FinanceChartResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, FinanceChartResponse::class.java).body
                ?: throw Exception()

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        val meta = body.chart.result[0].meta
        val timestamps = body.chart.result[0].timestamp
        val quotes = body.chart.result[0].indicators.quote

        val start = simpleDateFormat.format(timestamps[0] * 1000)
        val end = simpleDateFormat.format(timestamps[4] * 1000)

        val stockSummaries = stockSummaryRepository.findByTransactionDateBetweenAndSymbol(start, end, symbol)

        for (i in timestamps.indices) {
            val transactionDate = simpleDateFormat.format(timestamps[i] * 1000)
            val stockSummary = stockSummaries.find { it.symbol == symbol && it.transactionDate == transactionDate }

            println(transactionDate)
            println(stockSummary)
            if (stockSummary != null) {
                stockSummary.close = quotes[0].close[i]
                stockSummary.high = quotes[0].high[i]
                stockSummary.low = quotes[0].low[i]
                stockSummary.open = quotes[0].open[i]
                stockSummary.volume = quotes[0].volume[i]
                stockSummary.timestamp = timestamps[i]
                stockSummary.updatedDate = Date.from(Instant.now())
                stockSummaryRepository.save(stockSummary)
            } else {
                val newStockSummary = StockSummary(
                        transactionDate = simpleDateFormat.format(timestamps[i] * 1000),
                        close = quotes[0].close[i],
                        high = quotes[0].high[i],
                        low = quotes[0].low[i],
                        open = quotes[0].open[i],
                        volume = quotes[0].volume[i],
                        timestamp = timestamps[i],
                        symbol = meta.symbol);

                stockSummaryRepository.save(newStockSummary)
            }
        }

        return stockSummaryRepository.findByTransactionDateBetweenAndSymbol("2023-05-12", "2023-05-15", "005930.KS")
    }

    private fun createStockSummary(quote: Quote, ): StockSummary {

    }
}