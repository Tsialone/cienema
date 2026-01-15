package com.cinema.dev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.dev.models.CategoriePrix;
import com.cinema.dev.models.CategoriePrixId;

@Repository
public interface CategoriePrixRepository extends JpaRepository<CategoriePrix, CategoriePrixId> {
}
