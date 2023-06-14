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
public class PacienteOutDto {

    private Long id;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String dni;
    private String numSegSocial;
    private String telefono;
    private String sexo;
    private String email;
    private String password;
    private Double peso;
    private Double altura;
    private List<String> enfermedadesDiagnosticadas;
    private List<String> intervenciones;
    private List<String> alergias;
    private Long medicoAsignado;
}
