package com.cinema.dev.repositories;

import com.cinema.dev.models.CategoriePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoriePlaceRepository extends JpaRepository<CategoriePlace, Long>, JpaSpecificationExecutor<CategoriePlace> {

}
