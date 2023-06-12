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
public class SolicitudConsultaInDto {

    private Long id;
    private String descripcion;
    private LocalDate fecha;
    private String estado;
    private List<MultipartFile> archivos;
    private Long pacienteId;
    private Long medicoId;
}
