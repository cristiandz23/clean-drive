package com.cleandriver.persistence;

import com.cleandriver.model.Employed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployedRepository extends JpaRepository<Employed,Long> {

    Optional<Employed> findByDni(String dni);

    @Modifying
    @Query("UPDATE Employed e SET e.isActive = :isActive WHERE e.id = :id")
    int updateIsActiveById(@Param("id") Long id, @Param("isActive") boolean isActive);


}
