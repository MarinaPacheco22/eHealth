package com.tfg.eHealth.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PacienteDto {

    private Long id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String numSegSocial;
    private String telefono;
    private String email;
    private String idioma;
    private Double peso;
    private Double altura;
    private HistorialClinicoDto historialClinico;
}
