package com.cinema.dev.repositories;

import com.cinema.dev.models.Bloc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlocRepository extends JpaRepository<Bloc, Long>, JpaSpecificationExecutor<Bloc> {

}
