package com.tfg.eHealth.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
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
    private LocalDate fechaNacimiento;

    @Column
    private String dni;

    @Column
    private String telefono;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String numeroDeColegiado;

    @Column
    private String especialidad;

    @Column
    private boolean activo;

    @OneToMany(mappedBy = "medicoAsignado")
    private List<Paciente> pacientesAsignados;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL)
    private List<SolicitudConsulta> solicitudesConsulta;

}

