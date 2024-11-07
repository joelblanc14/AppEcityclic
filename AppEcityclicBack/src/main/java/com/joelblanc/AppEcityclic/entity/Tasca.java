package com.joelblanc.AppEcityclic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Tasca")
public class Tasca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tascaId;

    @Column
    private String nom;

    @Column(nullable = false)
    private String descripcio;

    @ManyToOne
    @JoinColumn(name = "treballador_id")
    private Treballador treballador;

    @ManyToOne
    @JoinColumn(name = "projecte_id", nullable = false)
    @JsonIgnore
    private Projecte projecte;

    @Column(nullable = false)
    private String estat;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataInici;

    private LocalDateTime dataFi;

    @PrePersist
    protected void onCreate() {
        this.dataInici = LocalDateTime.now();
    }

    public void marcarComplet() {
        this.dataFi = LocalDateTime.now();
    }
}