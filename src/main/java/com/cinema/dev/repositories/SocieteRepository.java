package com.cinema.dev.repositories;

import com.cinema.dev.models.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SocieteRepository extends JpaRepository<Societe, Long>, JpaSpecificationExecutor<Societe> {

}
