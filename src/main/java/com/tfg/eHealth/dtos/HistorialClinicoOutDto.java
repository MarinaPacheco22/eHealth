package com.tfg.eHealth.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HistorialClinicoOutDto {

    private Long id;
    private List<String> enfermedadesDiagnosticadas;
    private List<String> intervenciones;
    private List<String> alergias;
    private List<String> medicacionActual;
    private List<PruebaMedicaDto> pruebasMedicas;
    private Long pacienteId;
}