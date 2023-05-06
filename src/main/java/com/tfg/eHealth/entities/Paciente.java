package com.tfg.eHealth.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellidos;

    @Column
    private String dni;

    @Column
    private String numSegSocial;

    @Column
    private String telefono;

    @Column
    private String email;

    @Column
    private String idioma;

    @Column
    private Double peso;

    @Column
    private Double altura;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "historial_clinico_id", referencedColumnName = "id")
    private HistorialClinico historialClinico;

}

