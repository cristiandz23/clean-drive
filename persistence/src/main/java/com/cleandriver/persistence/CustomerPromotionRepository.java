package com.cleandriver.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPromotionRepository extends JpaRepository<CustomerPromotion,Long> {
}
