package com.cinema.dev.repositories;

import com.cinema.dev.models.Commande;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommandeRepository extends JpaRepository<Commande, Long>, JpaSpecificationExecutor<Commande> {

	// Find commandes by societe id
	List<Commande> findBySocieteIdSociete(Long idSociete);

}
