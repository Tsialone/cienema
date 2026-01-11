package com.cinema.dev.repositories;

import com.cinema.dev.models.ClientTicket;
import com.cinema.dev.models.ClientTicketId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientTicketRepository extends JpaRepository<ClientTicket, ClientTicketId>, JpaSpecificationExecutor<ClientTicket> {

}
