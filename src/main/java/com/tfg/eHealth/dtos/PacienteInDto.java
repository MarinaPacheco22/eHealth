package com.tfg.eHealth.dtos;

import com.tfg.eHealth.vo.SexoEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PacienteInDto {

    private Long id;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String dni;
    private String numSegSocial;
    private String telefono;
    private String email;
    private String password;
    private String sexo;
    private Boolean fumador;
    private Double peso;
    private Double altura;
    private HistorialClinicoInDto historialClinico;
    private MedicoDto medicoAsignado;
}
