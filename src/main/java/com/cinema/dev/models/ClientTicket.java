package com.cinema.dev.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "client_ticket")
@IdClass(ClientTicketId.class)
public class ClientTicket {

    @Id
    @Column(name = "id_client", nullable = false)
    private Long idClient;

    @Id
    @Column(name = "id_ticket", nullable = false)
    private Long idTicket;

}
