package com.cinema.dev.repositories;

import com.cinema.dev.models.Caisse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CaisseRepository extends JpaRepository<Caisse, Long>, JpaSpecificationExecutor<Caisse> {

}
