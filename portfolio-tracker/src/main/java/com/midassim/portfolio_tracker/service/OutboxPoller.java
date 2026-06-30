package com.midassim.portfolio_tracker.service;

import com.midassim.portfolio_tracker.entity.OutboxEvent;
import com.midassim.portfolio_tracker.repository.OutboxEventRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class OutboxPoller {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String,String> kafkaTemplate;

    public OutboxPoller(OutboxEventRepository outboxEventRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.outboxEventRepository = outboxEventRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    // fixedRate=5000 demek bu kodu her 5000 milisaniyede 5 saniyede bir tekrar çalıştır demek
    @Scheduled(fixedRate = 5000)
    public void pollOutboxEvents(){
        // 0. sayfadan başla ve bana sadece 100 tane getir

        PageRequest limit = PageRequest.of(0,100);
        // 1.kutuya bak : durumu "PENDING" olan olayları getir
        List<OutboxEvent> pendingEvents = outboxEventRepository.findByStatus("PENDING", (Pageable) limit);

        // Eğer kutu boşsa hiçbir şey yapma
        if (pendingEvents.isEmpty())
            return;

        System.out.println("Pending events count : " + pendingEvents.size());

        // kutudaki her siparişi al ve tek tek al ve işle
        for (OutboxEvent event : pendingEvents){
                try{
                    // BURASI KAFKAYA GİDECEK KISIM
                    // "trade-events" adındaki topice olayın ID'sini  anahtar yaparak JSON mesajımızı fırlatıyoruz
                    kafkaTemplate.send("trade-events",event.getId().toString(),event.getPayload());
                    System.out.println("Message is sending to kafka... Message: " + event.getPayload());

                    // gönderilmişse başarıyla durumunu SENT olarak güncelle
                    event.setStatus("SENT");

                }catch (Exception e){
                    // Eğer gönderilirken internet koparsa FAILED YAPIP  sonra tekrar deneyebiliriz
                    event.setStatus("FAILED");
                    System.out.println("Gönderim hatası: "  + e.getMessage());
                }
        }
        // güncellenmiş durumları SENT veritabanına geri kaydet
        outboxEventRepository.saveAll(pendingEvents);

    }
}
