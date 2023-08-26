package com.tfg.eHealth.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PruebaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime fechaHoraCita;

    @Column
    private String prueba;

    @Column
    private String consulta;

    @Column
    private String resultadosUrl;

    @ManyToOne
    @JoinColumn(name = "solicitud_consulta_id")
    private SolicitudConsulta solicitudConsulta;

}

