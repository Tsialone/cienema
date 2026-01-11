package com.cinema.dev.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDetail {
    private String ticketStrId;
    private String blocStrId;
    private String salleStrId;
    private Long numeroPlace;
    private String clientStrId;
    private String codeGroupe;
    private LocalDateTime datePrevue;
    private BigDecimal prix;
}
