package com.seovalue.m2price.adapter.outgoing

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PublicAptTradeApi {
    @GET("OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev")
    fun getAptTradeData(
        @Query("LAWD_CD") lawdCode: String?,
        @Query("DEAL_YMD") dealYmd: String?,
        @Query("serviceKey") serviceKey: String?,
        @Query("pageNo") pageNo: Int = 1,
        @Query("numOfRows") numOfRows: Int = 100,
    ): Call<AptTradeResponse>
}

@Xml(name = "response")
data class AptTradeResponse(
    @Element(name = "body")
    var body: Body?,
    @Element(name="header")
    var header: Header
)

@Xml(name="header")
data class Header(
    @PropertyElement(name="resultCode")
    var resultCode: Int,
    @PropertyElement(name="resultMsg")
    var resultMsg: String
)

@Xml(name = "body")
data class Body(
    @Element(name="items")
    var items: Items,
    @PropertyElement(name="numOfRows")
    var numOfRows: Int,
    @PropertyElement(name="pageNo")
    var pageNo: Int,
    @PropertyElement(name="totalCount")
    var totalCount: Int
)

@Xml(name= "items")
data class Items(
    @Element(name="item")
    var item: List<Item>
)

@Xml
data class Item(
    @PropertyElement(name = "거래금액")
    var transactionAmount: String,
    @PropertyElement(name = "건축년도")
    var constructionYear: String,
    @PropertyElement(name="법정동")
    var legalDong: String,
    @PropertyElement(name="아파트")
    var apartment: String,
    @PropertyElement(name="전용면적")
    var exclusiveArea: String,
)
