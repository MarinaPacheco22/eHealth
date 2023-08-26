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
public class MedicoInDto {

    private Long id;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;
    private String dni;
    private String telefono;
    private String email;
    private String password;
    private String numeroDeColegiado;
    private String sexo;
    private String especialidad;
    private Boolean activo;
    private List<PacienteInDto> pacientesAsignados;
}
