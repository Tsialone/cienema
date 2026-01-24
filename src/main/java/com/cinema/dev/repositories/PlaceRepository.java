package com.cinema.dev.repositories;

import com.cinema.dev.models.Place;
import com.cinema.dev.models.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long>, JpaSpecificationExecutor<Place> {
    
    Place findByNumero(Long numero);
    
    List<Place> findBySalle(Salle salle);

}
