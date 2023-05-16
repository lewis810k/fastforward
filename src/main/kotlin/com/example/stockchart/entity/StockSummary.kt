package com.example.stockchart.entity

import jakarta.persistence.*

@Entity
@Table(name = "StockSummary")
data class StockSummary (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int,
    var low: Int,
)