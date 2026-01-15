package com.cinema.dev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.dev.models.PrixC;

@Repository
public interface PrixCRepository extends JpaRepository<PrixC, Long> {
}
