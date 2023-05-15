package com.example.stockchart

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockChartApplication

fun main(args: Array<String>) {
    runApplication<StockChartApplication>(*args)
}
