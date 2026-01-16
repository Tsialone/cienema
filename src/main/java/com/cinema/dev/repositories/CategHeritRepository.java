package com.cinema.dev.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cinema.dev.models.CategHerit;

public interface CategHeritRepository extends JpaRepository<CategHerit, Long>, JpaSpecificationExecutor<CategHerit> {
    @Query("SELECT ch FROM CategHerit ch WHERE ch.categorie.idCategorie = :idCategorie OR ch.categorie1.idCategorie = :idCategorie")
    Optional<CategHerit> findByIdCategorie(@Param("idCategorie") Long idCategorie);
}
