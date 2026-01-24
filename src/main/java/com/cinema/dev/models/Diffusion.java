package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dev.forms.DiffusionForm;
import com.cinema.dev.repositories.DiffusionRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "diffusion")
public class Diffusion {

    @Id
    @Column(name = "id_diffusion", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDiffusion;

    @ManyToOne
    @JoinColumn(name = "id_seance", nullable = false)
    private Seance seance;

    @ManyToOne
    @JoinColumn(name = "id_pub", nullable = false)
    private Pub pub;

    @OneToOne(mappedBy = "diffusion", cascade = CascadeType.ALL, orphanRemoval = true)
    private CommandeDetail commandeDetail;

    @Transient
    public final String str = "DIF";

    @Transient
    public String getStrId() {
        return str + idDiffusion;
    }

    @Transient
    public static Double getCa(LocalDate date, DiffusionRepository diffusionRepository) {
        Double resp = 0.0;
        List<Diffusion> diffusions = getByDate(diffusionRepository, date);
        for (Diffusion diffusion : diffusions) {
            if (diffusion.getSeance() != null) {
                resp += diffusion.getSeance().getPrixPub().doubleValue();
            }
        }

        return resp;

    }

    @Transient
    public static List<Diffusion> getByDate(DiffusionRepository diffusionRepository, LocalDate date) {

        List<Diffusion> resp = new ArrayList<>();
        // LocalDate dateFiltre = date.w

        for (Diffusion diffusion : diffusionRepository.findAll()) {
            // System.out.println("compare " +
            // diffusion.getSeance().getDateTimeDebut().toLocalDate().withDayOfMonth(1) + "
            // et " + dateFiltre);
            LocalDate diffusionDate = diffusion.getSeance().getDateTimeDebut().toLocalDate().withDayOfMonth(1);
            if (diffusionDate.isBefore(date) || diffusionDate.isEqual(date)) {
                resp.add(diffusion);
            }
        }

        return resp;

    }

    @Transient
    public static void saveByForm(DiffusionForm form, DiffusionRepository diffusionRepository, Seance seance, Pub pub) {
        for (int i = 0; i < form.getNbrPub(); i++) {
            Diffusion diffusion = Diffusion.builder()
                    .seance(seance)
                    .pub(pub)
                    .build();
            diffusionRepository.save(diffusion);
        }
    }

    @Transient
    public static Diffusion save(Diffusion diffusion, DiffusionRepository diffusionRepository) {
        return diffusionRepository.save(diffusion);
    }
}
