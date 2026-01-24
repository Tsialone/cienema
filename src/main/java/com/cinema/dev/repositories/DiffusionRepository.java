package com.cinema.dev.repositories;

import com.cinema.dev.models.Diffusion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DiffusionRepository extends JpaRepository<Diffusion, Long>, JpaSpecificationExecutor<Diffusion> {

}
