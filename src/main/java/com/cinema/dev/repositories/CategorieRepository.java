package com.cinema.dev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cinema.dev.models.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long>, JpaSpecificationExecutor<Categorie> {
}
