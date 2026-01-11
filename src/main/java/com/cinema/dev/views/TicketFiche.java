package com.cinema.dev.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketFiche {
    private String ticketStrId;
    private String placeStrId;
    private String filmNom;
    private String clientStrId;
    private String datePrevue;
    private Double montantTotal;
    private Double restePaye;
    // private Integer nbrTicket;
    private String codeGroupe;
    private List<String> ticketStrIds;
}
