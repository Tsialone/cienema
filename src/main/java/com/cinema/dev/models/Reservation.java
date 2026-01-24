package com.cinema.dev.models;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.apache.bcel.generic.InstructionConstants.Clinit;

import com.cinema.dev.forms.ReservationForm;
import com.cinema.dev.repositories.CaisseRepository;
import com.cinema.dev.repositories.CategHeritRepository;
import com.cinema.dev.repositories.CategorieRepository;
import com.cinema.dev.repositories.ClientRepository;
import com.cinema.dev.repositories.FilmRepository;
import com.cinema.dev.repositories.MouvementCaisseRepository;
import com.cinema.dev.repositories.PaiementRepository;
import com.cinema.dev.repositories.PlaceRepository;
import com.cinema.dev.repositories.RemiseRepository;
import com.cinema.dev.repositories.ReservationRepository;
import com.cinema.dev.repositories.SalleRepository;
import com.cinema.dev.repositories.SeanceRepository;
import com.cinema.dev.repositories.TicketRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "reservation")
public class Reservation {

    @Id
    @Column(name = "id_reservation", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    @Column(name = "date_reservation", nullable = false)
    private LocalDateTime dateReservation;

    @OneToMany(mappedBy = "reservation" , fetch = FetchType.EAGER)
    private List<Ticket> tickets;


    @Transient
    public final String str = "RES";

    @Transient
    public String getStrId() {
        return str + idReservation;
    }
    @Transient
    public Double montant ;
    @Transient
    public Double getMontantTotal (RemiseRepository remiseRepository , CategHeritRepository categHeritRepository , CategorieRepository categorieRepository) {
        if (this.tickets == null || this.tickets.size() ==0){
            return 0.0;
        }
        Double total = 0.0;
       for (Ticket ticket : tickets) {
            // Remise remise = Remise.getRemiseByIdCategPlaceClientByDateTime(ticket.getPlace().getCategoriePlace().getIdCp(), ticket.getClient().getCategorie().getIdCategorie(),  this.getDateReservation(), remiseRepository).orElse(null);
            
            Double montant = ticket.getMontant(categHeritRepository, categorieRepository, remiseRepository);
            System.out.println("montant ticket id " + ticket.getIdTicket() + " : " + montant);
            // if (remise != null) {
            //     // montant =  remise.getMontant().doubleValue();
            // }
            total += montant;
        }
        return total;
    }
    @Transient
    public Client getClient (){
        if (this.tickets == null || this.tickets.size() ==0){
            return null;
        }
        return this.tickets.get(0).getClient();
    }
    @Transient
    public Integer getNbrTicket (){
        if (this.tickets == null ){
            return 0;
        }
        return this.tickets.size();
    }
    @Transient
    @Transactional
    public static Reservation save (ReservationForm reservationForm , ReservationRepository reservationRepository , PlaceRepository placeRepository  , TicketRepository ticketRepository , ClientRepository clientRepository , FilmRepository filmRepository , SeanceRepository seanceRepository , PaiementRepository paiementRepository , MouvementCaisseRepository mouvementCaisseRepository , CaisseRepository caisseRepository , RemiseRepository remiseRepository , CategHeritRepository categHeritRepository , SalleRepository salleRepository) throws Exception {
        reservationForm.control();
        Reservation reservation= new Reservation();
        reservation.setDateReservation(reservationForm.getDateTimeSeance());
        reservation = reservationRepository.save(reservation);
    
        List<Ticket> tickets =  new ArrayList<>();
        Salle salle = salleRepository.findById(reservationForm.getIdSalle()).orElseThrow( () -> new Exception("Invalid salle ID"));

        List<Place> placeDispos = salle.getPlaceDispo(reservation.getDateReservation(), reservationForm.getIdCp(), ticketRepository);
        if (placeDispos.size() <  reservationForm.getNombrePlaces()) {
            throw new Exception("Pas assez de places disponibles. Il reste seulement " + placeDispos.size() + " places disponibles pour cette catégorie.");
        }




        for (int index = 0; index < reservationForm.getNombrePlaces(); index++) {
            Place place  =  placeDispos.get(index) ; 
            Client client=  clientRepository.findById(reservationForm.getIdClient()).orElseThrow(() -> new Exception("Invalid client ID"));
            Film film =  filmRepository.findById(reservationForm.getIdFilm()).orElseThrow(() -> new Exception("Invalid film ID"));
            Ticket ticket = new Ticket();
            Seance seance = seanceRepository.findById(reservationForm.getIdSeance()).orElseThrow(() -> new Exception("Invalid seance ID"));
            ticket.setPlace(place);
            ticket.setClient(client);
            ticket.setFilm(film);
            ticket.setSeance(seance);
            ticket.setReservation(reservation);
            ticket.setCreated(LocalDateTime.now());
            ticket = ticketRepository.save(ticket);
            tickets.add(ticket);   
        }
      
        // reservation.setTickets(tickets);
        // for (Ticket ticket : tickets) {
        //     Long idcl = ticket.getClient().getIdClient();
        //     LocalDateTime dateReservation = reservation.getDateReservation();   
        //     Categorie him = ticket.getClient().getCategorie();

        //     Double montant =  him.getRealPrix(idcl,  dateReservation  , categHeritRepository, remiseRepository);
        //     // Double montant = parent != null ? enfant.(reservation.getDateReservation()) - (enfant.getPrixPlace(reservation.getDateReservation()) * parent.getPourcentage(CategHeritRepository).doubleValue() / 100) : enfant.getPrixPlace(reservation.getDateReservation());
          
        //     // ticket.payer(montant, 1l, paiementRepository, mouvementCaisseRepository, caisseRepository);
        // }
        
        return reservation;
    }
}
