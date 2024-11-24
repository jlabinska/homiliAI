package com.homiliai.core.repository;

import com.homiliai.core.entity.Sermon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SermonRepository extends JpaRepository<Sermon, Long> {

}
