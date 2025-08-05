package com.cleandriver.persistence;

import com.cleandriver.model.Employed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployedRepository extends JpaRepository<Employed,Long> {

    Optional<Employed> findByDni(String dni);

}
