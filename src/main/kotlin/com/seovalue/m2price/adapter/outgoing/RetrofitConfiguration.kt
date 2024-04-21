package com.seovalue.m2price.adapter.outgoing

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Configuration
@ConfigurationProperties(prefix = "m2price.apartment-transaction.rest")
open class RetrofitConfiguration {
    var host: String? = null
    var maxIdleConnections = 30
    var keepAliveDuration: Long = 5
    var connectTimeoutMillis: Long = 5000
    var readTimeoutMillis: Long = 5000

    @Bean
    open fun apartmentTransactionRetrofit(): Retrofit {
        val apartmentTransactionHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(connectTimeoutMillis, TimeUnit.MILLISECONDS)
            .readTimeout(readTimeoutMillis, TimeUnit.MILLISECONDS)
            .connectionPool(ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MINUTES))
            .build()
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()

        return Retrofit.Builder()
            .baseUrl(host!!)
            .addConverterFactory(TikXmlConverterFactory.create(parser))
            .client(apartmentTransactionHttpClient)
            .build()
    }

    @Bean
    open fun publicAptTradeApi(apartmentTransactionRetrofit: Retrofit): PublicAptTradeApi {
        return apartmentTransactionRetrofit.create(PublicAptTradeApi::class.java)
    }
}
