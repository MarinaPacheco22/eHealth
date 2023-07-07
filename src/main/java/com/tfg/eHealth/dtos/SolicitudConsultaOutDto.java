package com.tfg.eHealth.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SolicitudConsultaOutDto {

    private Long id;
    private String descripcion;
    private LocalDate fecha;
    private String estado;
    private Long pacienteId;
    private MedicoOutDto medicoOutDto;
    private int numArchivos;
}
