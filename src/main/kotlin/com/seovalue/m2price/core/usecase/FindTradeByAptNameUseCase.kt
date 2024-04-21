package com.seovalue.m2price.core.usecase

import com.seovalue.m2price.adapter.outgoing.Item
import com.seovalue.m2price.adapter.outgoing.PublicAptTradeApi
import org.springframework.stereotype.Service

@Service
class FindTradeByAptNameUseCase(
    private val publicAptTradeApi: PublicAptTradeApi
) {
    fun execute(apartment: String): Output {
        var pageNo = 1
        var totalPages = 1
        var totalCount = 0
        var result: Item? = null

        while (pageNo <= totalPages) {
            val response = publicAptTradeApi.getAptTradeData(
                lawdCode = "11710",
                dealYmd = "202403",
                serviceKey = "",
                pageNo = pageNo,
            ).execute().body()

            if (response?.body == null) {
                throw RuntimeException("Failed to get apt trade data")
            }

            val body = response.body!!

            // 첫 번째 페이지 호출 시, 총 아이템 수(totalCount)와 페이지 수(totalPages)를 설정
            if (pageNo == 1) {
                totalCount = body.totalCount
                totalPages = if (totalCount > 0) {
                    (totalCount + body.numOfRows - 1) / body.numOfRows // 총 아이템 수를 페이지 당 아이템 수로 나눈 페이지 수
                } else {
                    0
                }
            }

            // 아파트명에 해당하는 거래 정보 찾기
            val apartTradeData = body.items.item.firstOrNull { it.apartment.trim().contains(apartment.trim()) }

            if (apartTradeData != null) {
                result = apartTradeData
                break // 아파트명에 해당하는 거래 정보가 있으면 반복 중지
            }

            pageNo++ // 다음 페이지 호출을 위해 페이지 번호 증가
        }

        if (result == null) {
            throw RuntimeException("Failed to find trade data for apartment: $apartment")
        }
        return Output(
            apartment = result.apartment,
            transactionAmount = result.transactionAmount,
            constructionYear = result.constructionYear,
            exclusiveArea = result.exclusiveArea,
        )
    }
}

data class Output(
    val apartment: String,
    val transactionAmount: Int,
    val constructionYear: String,
    val exclusiveArea: Double,
) {
    constructor(
        apartment: String,
        transactionAmount: String,
        constructionYear: String,
        exclusiveArea: String,
    ) : this(
        apartment = apartment,
        transactionAmount = transactionAmount.replace(",", "").toInt(),
        constructionYear = constructionYear,
        exclusiveArea = exclusiveArea.toDouble(),
    )
}
