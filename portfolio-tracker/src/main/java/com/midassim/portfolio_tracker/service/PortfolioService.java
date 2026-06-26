package com.midassim.portfolio_tracker.service;


import com.midassim.portfolio_tracker.entity.OutboxEvent;
import com.midassim.portfolio_tracker.entity.User;
import com.midassim.portfolio_tracker.repository.OutboxEventRepository;
import com.midassim.portfolio_tracker.repository.PortfolioRepository;
import com.midassim.portfolio_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service // business logic
public class PortfolioService {

    // service katmanı rositoryleri kullanmak zorunda dependency injection
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final OutboxEventRepository outboxEventRepository;

    public PortfolioService(UserRepository userRepository, PortfolioRepository portfolioRepository, OutboxEventRepository outboxEventRepository) {
        this.userRepository = userRepository;
        this.portfolioRepository = portfolioRepository;
        this.outboxEventRepository = outboxEventRepository;
    }

    // İşte o sihirli kalkanımız : Bu metodun içindeki işlemler ya hep ya hiç kuralıyla çalışırlar
    @Transactional
    public void buyAsset(Long userId, Long assetId, BigDecimal amount, BigDecimal currentPrice){

        // Kullanıcıya repodan bul ya da hata fırlat
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        // Toplam maliyeti heasapla (miktar * güncel fiyat)
        BigDecimal totalCost = amount.multiply(currentPrice);

        // Validasyon : Para yetiyor mu?
        if (user.getBalance().compareTo(totalCost) < 0 ){
            throw new RuntimeException("Yetersiz bakiye!İşlem reddedildi");
        }
        // Parayı hesaptan düş (İşlem 1 )
        user.setBalance(user.getBalance().subtract(totalCost));
        userRepository.save(user); // kullanıcıyı güncel bakiyesiyle kaydet

        OutboxEvent event = new OutboxEvent();
        event.setEventType("ORDER_CREATED");
        event.setStatus("PENDING");

        // Payload'ı (Mesajı) basit bir JSON metni olarak oluşturuyoruz
        String payloadJson = String.format(
                "{\"userId\": %d, \"assetId\": %d, \"amount\": %s, \"price\": %s}",
                userId, assetId, amount.toString(), currentPrice.toString()
        );
        event.setPayload(payloadJson);

        // Kutuya kaydet! (Hepsi aynı Transaction içinde olduğu için mükemmel güvenli)
        outboxEventRepository.save(event);
    }
}
