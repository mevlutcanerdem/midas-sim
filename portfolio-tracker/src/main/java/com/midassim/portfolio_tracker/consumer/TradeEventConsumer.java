package com.midassim.portfolio_tracker.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TradeEventConsumer {
    // Idempotency (Aynı mesajı iki kere işlemek) için uydurma bir veritabanı simülasyonu
    // Gerçek hayatta bu liste Rediste veya ayrı bir PostgreSQL de tutulur
    private final Set<String> processedEventIds = new HashSet<>();


    // Telsizin frekansını (topic ) ve takımımınzın adını (groupId) belirliyoruz
    @KafkaListener(topics = "trade-events",groupId = "midas-notification-group")
    public void consumeTradeEvent(String payload) {
        System.out.println("Telsizden anons geldi! Yeni sipariş alındı : " + payload);

        // 1. JSON metnini (payload) parçala ve  içinden eventId yi bul
        String eventId = extractEventIdFromJson(payload);

        // 2. IdemPotency Kontrolü : bu bileti daha önce okuttuk mu
        if (processedEventIds.contains(eventId)) {
            System.out.println("Uyarı: " + eventId + " numaralı işlem daha önce yapılmış.Yok sayılıyor");
            return;
        }


        try {
            // 3. Asıl iş : burada sse ile kullanıcının telefonuna hisseniz alındı bildirimi atabilriiz
            System.out.println("Bildirim gönderiliyor... İşlem başarıyla tamamlandı");
            // 4. Bilet başarıyla okutuldu , listeye ekle ki bir daha gelirse kanmayalım
            processedEventIds.add(eventId);
        } catch (Exception e) {
            System.out.println("İşlem sırasında hata çıktı : " + e.getMessage());
            // hata çıkarsa listeye eklemeyiz ki kafka mesajı tekrar gönderdiğindde işleyebilelim
        }
    }

    // basit bir yardımcı metod
    private String extractEventIdFromJson(String payload) {
        // normalde burada jackson (ObjectMapper) kütüphanesi kullanıp  JSON objeye çevirir
        return "1453";
    }
}
