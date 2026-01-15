package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cinema.dev.forms.TicketForm;
import com.cinema.dev.repositories.PlaceRepository;
import com.cinema.dev.repositories.TicketRepository;
import com.cinema.dev.views.TicketFiche;
import com.cinema.dev.repositories.CaisseRepository;
import com.cinema.dev.repositories.ClientRepository;
import com.cinema.dev.repositories.FilmRepository;
import com.cinema.dev.repositories.MouvementCaisseRepository;
import com.cinema.dev.repositories.PaiementRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "ticket")
public class Ticket {

    @Id
    @Column(name = "id_ticket", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;

    @ManyToOne
    @JoinColumn(name = "id_seance", nullable = false)
    private Seance seance;

    @ManyToOne
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "id_place", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "id_film", nullable = false)
    private Film film;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @Transient
    private LocalDateTime datePrevue;

    @Transient
    private String codeGroupe;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Paiement> paiements = new ArrayList<>();

    @Transient
    public final String str = "TKT";

    @Transient
    public static List<Ticket> saveAll(List<Ticket> tickets, TicketRepository ticketRepository) {
        return ticketRepository.saveAll(tickets);
    }

    @Transient
    public Double getTotalPaye(LocalDateTime since) {
        Double total = 0.0;
        for (Paiement paiement : paiements) {
            if (since == null || paiement.getCreated().isBefore(since) || paiement.getCreated().isEqual(since)) {
                total += paiement.getMontant().doubleValue();
            }
        }
        return total;
    }

    @Transient
    public Double getTotalPaye() {
        return getTotalPaye(null);
    }

    @Transient
    public Double getRestPaye(LocalDateTime since) {
        // Double totalPrix = this.place.getBloc().getPrix().doubleValue();
        // Double totalPaye = getTotalPaye(since);
        return 0.0;
    }

    @Transient
    public Double getRestPaye() {
        return getRestPaye(null);
    }

    @Transient
    @Transactional
    public Paiement payer(Double montant, Long idCaisse, PaiementRepository paiementRepository,
            MouvementCaisseRepository mouvementCaisseRepository , CaisseRepository caisseRepository) throws Exception {
        if (montant <= 0) {
            throw new Exception("Montant doit être positif");
        }
        // Caisse caisse = Caisse.findByStrId(caisseRepository, strIdCaisse);
        Caisse caisse = caisseRepository.findById(idCaisse).orElseThrow(() -> new Exception("Caisse non trouvée"));

        Paiement paiement = new Paiement();
        paiement.setMontant(BigDecimal.valueOf(montant));
        paiement.setCreated(LocalDateTime.now());
        paiement.setTicket(this);

        MouvementCaisse mouvementCaisse = new MouvementCaisse();
        mouvementCaisse.setCredit(BigDecimal.valueOf(montant));
        mouvementCaisse.setDebit(BigDecimal.ZERO);
        mouvementCaisse.setCaisse(caisse);

        mouvementCaisse.save(mouvementCaisseRepository);

        paiement.save(paiementRepository, mouvementCaisse);

        return paiement;
    }

    // @Transient
    // public List<Paiement> toutPayer(Double montant, String strIdCaisse, TicketRepository ticketRepository,
    //         PaiementRepository paiementRepository,
    //         MouvementCaisseRepository mouvementCaisseRepository , CaisseRepository caisseRepository) throws Exception {
    //     List<Paiement> paiements = new ArrayList<>();
    //     List<Ticket> tickets = new ArrayList<>();
    //     tickets.add(this);
    //     tickets.addAll(this.getByAsocieGroupe(ticketRepository));
    //     double aPayer = montant / tickets.size();
    //     for (Ticket ticket : tickets) {
    //         Paiement paiement = ticket.payer(aPayer, strIdCaisse, paiementRepository, mouvementCaisseRepository , caisseRepository);
    //         paiements.add(paiement);
    //     }

    //     return paiements;
    // }

    @Transient
    public TicketFiche toFiche(TicketRepository ticketRepository) {
        TicketFiche fiche = new TicketFiche();
        fiche.setTicketStrId(this.getStrId());
        fiche.setDatePrevue(this.datePrevue.toString());
        fiche.setCodeGroupe(this.codeGroupe);
        fiche.setPlaceStrId(this.place.getStrId());
        fiche.setFilmNom(this.film.getNom());
        fiche.setClientStrId(this.client.getStrId());

        List<String> ticketStrIds = new ArrayList<>();
        // Double montantTotal = this.place.getBloc().getPrix().doubleValue();
        Double restePaye = this.getRestPaye(LocalDateTime.now());

        for (Ticket ticket : this.getByAsocieGroupe(ticketRepository)) {
            ticketStrIds.add(ticket.getStrId());
            // montantTotal += ticket.getPlace().getBloc().getPrix().doubleValue();
            restePaye+= ticket.getRestPaye(LocalDateTime.now());
        }
        fiche.setTicketStrIds(ticketStrIds);
        // fiche.setMontantTotal(montantTotal);
        fiche.setRestePaye(restePaye);
        return fiche;
    }

    @Transient
    public static Ticket findByStrId(TicketRepository ticketRepository, String ticketStrId) {
        for (Ticket ticket : getAll(ticketRepository)) {
            if (ticket.getStrId().equals(ticketStrId)) {
                return ticket;
            }
        }
        return null;
    }

    @Transient
    public Ticket save(TicketRepository ticketRepository) {
        return ticketRepository.save(this);
    }

    @Transient
    public List<Ticket> getByAsocieGroupe(TicketRepository ticketRepository) {
        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : ticketRepository.findByCodeGroupe(this.codeGroupe)) {
            if (!ticket.getIdTicket().equals(this.idTicket)) {
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    @Transient
    public static List<Ticket> getByCodeGroupe(TicketRepository ticketRepository, String codeGroupe) {
        return ticketRepository.findByCodeGroupe(codeGroupe);
    }

    @Transient
    public static List<Ticket> initByForm(
            TicketForm ticketForm,
            PlaceRepository placeRepository,
            TicketRepository ticketRepository,
            ClientRepository clientRepository,
            FilmRepository filmRepository) throws Exception {
        ticketForm.control();
        List<Ticket> tickets = new ArrayList<>();
        String codeGroupe = generateCodeGroupe(ticketRepository);

        Client client = clientRepository.findById(ticketForm.getClientId())
                .orElseThrow(() -> new Exception("Client not found"));

        for (Long numeroPlace : ticketForm.getNumeroPlace()) {
            Place place = Place.getByNumeroPlace(placeRepository, numeroPlace);
            if (place == null) {
                throw new Exception("Place with numero " + numeroPlace + " not found.");
            }

            Ticket ticket = new Ticket();
            ticket.setDatePrevue(ticketForm.getDatePrevue());
            ticket.setFilm(
                    filmRepository.findById(ticketForm.getIdFilm()).orElseThrow(() -> new Exception("Film not found")));
            ticket.setPlace(place);
            ticket.setClient(client);
            ticket.setCodeGroupe(codeGroupe);
            tickets.add(ticket);
        }
        return tickets;

    }

    @Transient
    public static List<Ticket> getAll(TicketRepository ticketRepository) {
        return ticketRepository.findAll();
    }

    @Transient
    public String getStrId() {
        return str + idTicket;
    }

    @Transient
    public static String generateCodeGroupe(TicketRepository ticketRepository) {
        Long nextVal = ticketRepository.getNextGroupeSeq();
        return "GRP" + nextVal;
    }

}
