package com.cinema.dev.repositories;

import com.cinema.dev.models.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SeanceRepository extends JpaRepository<Seance, Long>, JpaSpecificationExecutor<Seance> {

}
