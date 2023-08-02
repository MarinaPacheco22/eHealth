package com.tfg.eHealth.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ResolucionConsulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate fecha;

    @Column
    private String diagnostico;

    @Column
    private String tratamiento;

    @ManyToOne
    @JoinColumn(name = "solicitud_consulta_id")
    private SolicitudConsulta solicitudConsulta;

}

