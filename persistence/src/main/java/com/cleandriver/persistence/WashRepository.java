package com.cleandriver.persistence;

import com.cleandriver.model.Wash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WashRepository extends JpaRepository<Wash,Long> {
}
