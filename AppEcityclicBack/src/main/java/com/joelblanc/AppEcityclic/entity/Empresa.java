package com.joelblanc.AppEcityclic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empresaId;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String adreca;

    @Column(nullable = false, unique = true)
    private String cif;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Treballador> treballadors = new ArrayList<>();

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Projecte> projectes = new ArrayList<>();
}
