package com.joelblanc.AppEcityclic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Treballador")
public class Treballador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long treballadorId;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String posicio;

    @Column(nullable = false)
    private Double salari;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    @JsonBackReference
    private Empresa empresa;

    @ManyToMany(mappedBy = "treballadors")
    private List<Projecte> projectes;

}
