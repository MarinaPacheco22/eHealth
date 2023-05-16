package com.tfg.eHealth.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PruebaMedicaDto {

    private Long id;
    private LocalDate fecha;
    private String nombre;
    private String diagnostico;
    private String tratamiento;
    private HistorialClinicoInDto historialClinico;
}
