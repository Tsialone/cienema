package com.cinema.dev.repositories;

import com.cinema.dev.models.PaiementCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaiementCommandeRepository extends JpaRepository<PaiementCommande, Long>, JpaSpecificationExecutor<PaiementCommande> {

}
