package com.example.stockchart.controller

import com.example.stockchart.entity.StockSummary
import com.example.stockchart.service.StockSummaryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@RestController()
@RequestMapping("/stock-summaries")
class StockSummaryController(
        private val service: StockSummaryService
) {
    @GetMapping
    fun findStockSummary(
            @RequestParam(required = true, value = "종목코드", defaultValue = "005930.KS") symbol: String,
            @RequestParam(required = true, value = "검색일자범위 (1d, 5d)", defaultValue = "5d") range: String): List<StockSummary> {
        return service.findStockSummary(symbol, range)
    }
}