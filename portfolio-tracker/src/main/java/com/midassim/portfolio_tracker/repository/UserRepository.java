package com.midassim.portfolio_tracker.repository;

import com.midassim.portfolio_tracker.entity.User;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // springe bu sınıfın veritabanı işlerine baktığını söyler
public interface UserRepository extends JpaRepository<User,Long> {
    // JpaRepository<Hangi Tablo/Entity, Bu tablonun Id tipi>
    // tüm metodlar içinde hazır olarak geliyor kod yazmamıza gerek yok
}
