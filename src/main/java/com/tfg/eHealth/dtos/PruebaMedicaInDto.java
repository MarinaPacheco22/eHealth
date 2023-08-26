package com.tfg.eHealth.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PruebaMedicaInDto {

    private String fechaHoraCita;
    private String prueba;
    private String consulta;
    private String resultadosUrl;
    private Long solicitudId;
}
