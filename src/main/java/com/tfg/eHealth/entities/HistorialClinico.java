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
public class HistorialClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> enfermedadesDiagnosticadas;

    @ElementCollection
    private List<String> intervenciones;

    @ElementCollection
    private List<String> alergias;

    @ElementCollection
    private List<String> medicacionActual;

    @OneToOne(mappedBy = "historialClinico")
    private Paciente paciente;

}

