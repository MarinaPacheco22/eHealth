package com.tfg.eHealth.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResolucionConsultaInDto {

    private Long id;
    private String observacion;
    private String diagnostico;
    private String tratamiento;
    private String medicacion;
    private String enfermedad;
    private String alergia;
    private Long solicitudConsultaId;
    private Long pacienteId;
}
