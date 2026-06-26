package com.midassim.portfolio_tracker.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ("ORDER_CREATED","USER_REGISTERED")
    private String eventType;

    // olaya ait asıl veri (ör: "Ali 10 adet thy aldı bilgisini JSON formatında (metin olarak) burada tutarız")

    @Column(columnDefinition = "TEXT")
    private String payload;

    // olayın durumu : PENDING(bekliyor),SENT(Kafka), FAILED (HATA ALDI)
    private String status;

    // olayın veritabanına yazıldığı tam an
    private LocalDateTime createdAt;

    // Sınıf yaratılırken createdAt otamatik olarak şu anki zaman olsun
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

