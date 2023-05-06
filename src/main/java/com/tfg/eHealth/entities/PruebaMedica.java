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
public class PruebaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate fecha;

    @Column
    private String nombre;

    @Column
    private String diagnostico;

    @Column
    private String tratamiento;

    @ManyToOne
    @JoinColumn(name = "historial_clinico_id")
    private HistorialClinico historialClinico;

}

