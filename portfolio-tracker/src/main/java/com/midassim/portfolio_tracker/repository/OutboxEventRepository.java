package com.midassim.portfolio_tracker.repository;

import com.midassim.portfolio_tracker.entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent,Long> {
    // spring data jpa in gücü sadece ismini yazıyoz sql i o arka planda üretiyor
    // bu method, outbox tablosundaki durumu  'PENDING' olan olayları bulup  bize liste olarak getirecek
    // Pageable parametresi sayesinde "bana sadece ilk 100 tanesini getir " diyebileceğiz
    List<OutboxEvent> findByStatus(String status, Pageable pageable );
}
