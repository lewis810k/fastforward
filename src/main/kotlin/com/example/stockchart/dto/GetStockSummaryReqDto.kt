package com.example.stockchart.dto

import io.swagger.annotations.ApiModelProperty

class GetStockSummaryReqDto(
        @ApiModelProperty(required = true, value = "종목코드 (default = 005930.KS)")
        val symbol: String = "005930.KS",

        @ApiModelProperty(required = true, value = "검색일자범위 (default: 5d)")
        val range: String = "5d"
)