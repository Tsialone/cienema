package com.cinema.dev.repositories;

import com.cinema.dev.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {
    @Query(value = "SELECT nextval('groupe_seq')", nativeQuery = true)
    Long getNextGroupeSeq();

    List<Ticket> findByCodeGroupe(String codeGroupe);
}
