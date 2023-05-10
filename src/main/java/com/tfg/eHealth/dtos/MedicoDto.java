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
public class MedicoDto {

    private Long id;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String dni;
    private String telefono;
    private String email;
    private String password;
    private String numeroDeColegiado;
    private String especialidad;
    private boolean activo;
    private List<PacienteDto> pacientesAsignados;
}
