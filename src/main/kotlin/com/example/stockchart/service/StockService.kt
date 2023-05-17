package com.example.stockchart.service

import com.example.stockchart.dto.FinanceChartResponse
import com.example.stockchart.entity.StockSummary
import com.example.stockchart.repository.StockSummaryRepository
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class StockService(
        val stockSummaryRepository: StockSummaryRepository
) {

    fun getCharts(): FinanceChartResponse {
        val restTemplate: RestTemplate = RestTemplateBuilder().build()
        val builder: UriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("https://query1.finance.yahoo.com/v8/finance/chart/005930.KS?interval=1d&range=5d")

        val body: FinanceChartResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, FinanceChartResponse::class.java).body
                ?: throw Exception()

        // 해당 날짜 데이터 있으면 반환
        // 없으면 조회 / 저장 후 반환
        val meta = body.chart.result[0].meta
        val timestamps = body.chart.result[0].timestamp
        val quotes = body.chart.result[0].indicators.quote

        for (i in 0..4) {
            val stockSummary = StockSummary(
                    close = quotes[0].close[i],
                    high = quotes[0].high[i],
                    low = quotes[0].low[i],
                    open = quotes[0].open[i],
                    volume = quotes[0].volume[i],
                    timestamp = timestamps[i],
                    symbol = meta.symbol);

            stockSummaryRepository.save(stockSummary)
        }

        return body
    }
}