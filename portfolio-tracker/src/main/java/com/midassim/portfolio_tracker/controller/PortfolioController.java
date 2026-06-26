package com.midassim.portfolio_tracker.controller;


import com.midassim.portfolio_tracker.service.PortfolioService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@Controller
public class PortfolioController {

    // controller gelen istekleri service iletir
    // dependency injection ile service katmanını buraya çağırıyoruz
    private final PortfolioService portfolioService;


    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    // @MutationMapping: Veritabanındaki bir şey değiştirecek (ekleme/silme/güncelleme) istekleri yakalar
    // Metod adı : GraphQL şemasında yazdığımız isimle (placeOrder vs. ) eşleşmelidir

    @MutationMapping
    public String buyAsset(@Argument Long userId,
                           @Argument Long assetId,
                           @Argument BigDecimal amount,
                           @Argument BigDecimal currentPrice){

        // controller zeki değildir, hesap yapmaz Gelen siparişi aynen service e iletir.

        try {
                portfolioService.buyAsset(userId,assetId,amount,currentPrice);
                return "İşlem başarılı.Hisseler cüzdanınıza eklendi";
        }catch (Exception e){
            return "İşlem başarısız: " + e.getMessage();
        }

    }




}
