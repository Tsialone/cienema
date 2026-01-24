package com.cinema.dev.repositories;

import com.cinema.dev.models.CommandeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommandeDetailRepository extends JpaRepository<CommandeDetail, Long>, JpaSpecificationExecutor<CommandeDetail> {

}
