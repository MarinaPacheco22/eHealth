package com.tfg.eHealth.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PruebaMedicaOutDto {

    private Long id;
    private LocalDateTime fechaHoraCita;
    private String prueba;
    private String consulta;
    private String resultadosUrl;
    private PacienteOutDto pacienteOutDto;
    private MedicoOutDto medicoOutDto;
    private SolicitudConsultaOutDto solicitudConsultaOutDto;
}
