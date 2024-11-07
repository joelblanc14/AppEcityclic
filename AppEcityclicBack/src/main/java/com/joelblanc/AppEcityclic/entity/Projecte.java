package com.joelblanc.AppEcityclic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Projecte")
public class Projecte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projecteId;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String estat;

    @Column
    private String descripcio;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    @JsonIgnore
    private Empresa empresa;

    @ManyToMany
    @JoinTable(
            name = "projecte_treballador",
            joinColumns = @JoinColumn(name = "projecte_id"),
            inverseJoinColumns = @JoinColumn(name = "treballador_id")
    )
    private List<Treballador> treballadors;

    @OneToMany(mappedBy = "projecte", cascade = CascadeType.ALL)
    private List<Tasca> tasques;

    @ManyToMany
    @JoinTable(
            name = "client_projecte",
            joinColumns = @JoinColumn(name = "projecte_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

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
