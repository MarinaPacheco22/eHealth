package com.tfg.eHealth.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Medico {

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
    private String telefono;

    @Column
    private String email;

    @Column
    private String numeroDeColegiado;

    @Column
    private String especialidad;

    @ElementCollection
    private List<String> idiomas;

    @Column
    private boolean activo;

}

