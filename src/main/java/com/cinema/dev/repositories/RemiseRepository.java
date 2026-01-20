package com.cinema.dev.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cinema.dev.models.Remise;

public interface RemiseRepository extends JpaRepository<Remise, Long>, JpaSpecificationExecutor<Remise> {
    
    @Query(value = "SELECT * FROM remise r WHERE r.id_cp = :idCategPlace " +
           "AND r.id_categorie = :idCategClient " +
           "AND r.created <= :dateTime " +
           "ORDER BY r.created DESC LIMIT 1", nativeQuery = true)
    Optional<Remise> findRemiseByIdCategPlaceClientByDateTime(
            @Param("idCategPlace") Long idCategPlace,
            @Param("idCategClient") Long idCategClient,
            @Param("dateTime") LocalDateTime dateTime);
}
