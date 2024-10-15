package com.homiliai.core.repository;

import com.homiliai.core.entity.Parish;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParishRepository extends JpaRepository<Parish, Long> {

  Optional<Parish> findByName(String name);
}


