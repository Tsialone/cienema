package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dev.repositories.PubRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "pub")
public class Pub {

    @Id
    @Column(name = "id_pub", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPub;

    @Column(name = "desc_")
    private String desc;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "id_societe", nullable = false)
    private Societe societe;

    @OneToMany(mappedBy = "pub", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Diffusion> diffusions = new ArrayList<>();

    @Transient
    public final String str = "PUB";

    @Transient
    public String getStrId() {
        return str + idPub;
    }

    @Transient
    public static List<Pub> getAll(PubRepository pubRepository) {
        return pubRepository.findAll();
    }
}
