package com.example.stockchart.entity

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "StockSummary")
data class StockSummary(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        @Column
        val symbol: String, // 종목코드

        @Column
        val low: Int, // 최저가격

        @Column
        val high: Int, // 최고가격

        @Column
        val open: Int, // 개장가격

        @Column
        val close: Int, // 종가가격

        @Column
        val volume: Long, // 거래량
//
        @Column
        val timestamp: Long, // 거래일자

        @Column
        val createdDate: Date, // 생성일시
)