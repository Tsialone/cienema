package com.cinema.dev.repositories;

import com.cinema.dev.models.MouvementCaisse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MouvementCaisseRepository extends JpaRepository<MouvementCaisse, Long>, JpaSpecificationExecutor<MouvementCaisse> {

}
