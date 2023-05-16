package com.example.stockchart.service

import com.example.stockchart.dto.FinanceChartResponse
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class StockService() {

    fun getCharts(): FinanceChartResponse {
        val restTemplate: RestTemplate = RestTemplateBuilder().build()
        val builder: UriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("https://query1.finance.yahoo.com/v8/finance/chart/005930.KS?interval=1d&range=5d")

        val body: FinanceChartResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, FinanceChartResponse::class.java).body
                ?: throw Exception()

        println(body)

        return body
    }
}