package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

import com.cinema.dev.views.SeanceDetail;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "seance")
public class Seance {

    @Id
    @Column(name = "id_seance", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeance;

    @Column(name = "date_time_debut", nullable = false)
    private LocalDateTime dateTimeDebut;

    @ManyToOne
    @JoinColumn(name = "id_salle", nullable = false)
    private Salle salle;

    @ManyToOne
    @JoinColumn(name = "id_film", nullable = false)
    private Film film;

    @Transient
    public final String str = "SEC";

    @Transient
    public String getStrId() {
        return str + idSeance;
    }

    @Transient
    public LocalDateTime getDateFinPrevue (){
        return this.dateTimeDebut.plusMinutes(this.film.getDuree().getHour()*60 + this.film.getDuree().getMinute());
    }

    @Transient
    public static  List<SeanceDetail> getSeanceDetail (LocalDateTime debut , LocalDateTime fin  , Long idSalle , Long idFilm ){
        
        return List.of();
        
    }

}
