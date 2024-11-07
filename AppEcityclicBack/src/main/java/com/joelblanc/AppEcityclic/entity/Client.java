package com.joelblanc.AppEcityclic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String adreca;

    @OneToMany(mappedBy = "client")
    private Set<Projecte> projectes;

    @ManyToOne
    @JoinColumn(name = "projecteId")
    private Projecte projecte;
}
