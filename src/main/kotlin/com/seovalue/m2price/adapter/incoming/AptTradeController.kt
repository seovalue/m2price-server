package com.seovalue.m2price.adapter.incoming

import com.seovalue.m2price.core.usecase.FindTradeByAptNameUseCase
import com.seovalue.m2price.core.usecase.Output
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AptTradeController(
    private val findTradeByAptNameUseCase: FindTradeByAptNameUseCase,
) {
    @GetMapping("/apt-trade")
    fun getAptTradeData(
        @RequestParam("aptName") aptName: String
    ): Output {
        return findTradeByAptNameUseCase.execute(aptName)
    }
}
