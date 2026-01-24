package com.cinema.dev.repositories;

import com.cinema.dev.models.Pub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PubRepository extends JpaRepository<Pub, Long>, JpaSpecificationExecutor<Pub> {

}
