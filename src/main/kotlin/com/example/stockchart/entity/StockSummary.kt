package com.example.stockchart.entity

import jakarta.persistence.*
import lombok.Getter
import lombok.Setter
import org.apache.commons.lang3.time.DateUtils
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@Entity
@Table(name = "StockSummary",
        uniqueConstraints = [UniqueConstraint(
                name = "symbol_transaction_date_unique_contraint",
                columnNames = ["symbol", "transactionDate"]
        )])
data class StockSummary(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        @Column(name = "symbol")
        val symbol: String, // 종목코드

        @Column(name = "transactionDate")
        val transactionDate: String, // 거래일자 (yyyy-MM-dd)

        @Column
        var low: Int, // 최저가격

        @Column
        var high: Int, // 최고가격

        @Column
        var open: Int, // 개장가격

        @Column
        var close: Int, // 종가가격

        @Column
        var volume: Long, // 거래량
//
        @Column
        var timestamp: Long, // 거래일시

        @CreatedDate
        var createdDate: Date = Date.from(Instant.now()), // 생성일시

        @LastModifiedDate
        var updatedDate: Date? = Date.from(Instant.now())
)