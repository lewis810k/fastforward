package com.example.stockchart.controller
import com.example.stockchart.dto.FinanceChartResponse
import com.example.stockchart.entity.StockSummary
import com.example.stockchart.service.StockService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@RestController()
@RequestMapping("/stocks")
class StockController(
        private val stockService: StockService
) {
    @GetMapping
    fun getCharts(): List<StockSummary> {
        return stockService.getCharts()
    }
}