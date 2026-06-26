package com.midassim.portfolio_tracker.repository;

import com.midassim.portfolio_tracker.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio,Long> {
}
