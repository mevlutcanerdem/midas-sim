package com.midassim.portfolio_tracker.repository;

import com.midassim.portfolio_tracker.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset,Long> {
    // ileride "bana sembolü thy
    // olan varlığı getir " demek istersek bburaya özel metodlar ekleyecez
}
