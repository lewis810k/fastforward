package com.example.stockchart

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
class StockChartApplication

fun main(args: Array<String>) {
    runApplication<StockChartApplication>(*args)
}
