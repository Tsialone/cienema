package com.cinema.dev.repositories;

import com.cinema.dev.models.Seance;
import com.cinema.dev.views.SeanceDetail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeanceRepository extends JpaRepository<Seance, Long>, JpaSpecificationExecutor<Seance> {


    @Query(nativeQuery = true, value = "SELECT s.id_seance, DATE(s.date_time_debut), s.id_salle, sa.nom, s.id_film, f.nom, f.duree, TO_CHAR(s.date_time_debut, 'HH24:MI') " +
           "FROM seance s " +
           "JOIN salle sa ON s.id_salle = sa.id_salle " +
           "JOIN film f ON s.id_film = f.id_film " +
           "WHERE (CAST(:debut AS date) IS NULL OR DATE(s.date_time_debut) = CAST(:debut AS date)) " +
           "AND (CAST(:idSalle AS bigint) IS NULL OR s.id_salle = :idSalle) " +
           "AND (CAST(:idFilm AS bigint) IS NULL OR s.id_film = :idFilm) " +
           "AND (CAST(:heure AS time) IS NULL OR TO_CHAR(s.date_time_debut, 'HH24:MI') = TO_CHAR(CAST(:heure AS time), 'HH24:MI'))")
    List<Object[]> filterByRaw(@Param("debut") LocalDate debut, 
                                @Param("idSalle") Long idSalle, 
                                @Param("idFilm") Long idFilm,
                                @Param("heure") LocalTime heure);

    default List<SeanceDetail> filterBy(LocalDate debut, Long idSalle, Long idFilm, LocalTime heure) {
        return filterByRaw(debut, idSalle, idFilm, heure).stream()
            .map(row -> new SeanceDetail(
                String.valueOf(row[0]),
                String.valueOf(row[1]),
                String.valueOf(row[2]),
                (String) row[3],
                String.valueOf(row[4]),
                (String) row[5],
                String.valueOf(row[6]),
                (String) row[7]
            ))
            .toList();
    }

}

