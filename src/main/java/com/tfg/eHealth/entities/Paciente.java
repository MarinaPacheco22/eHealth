package com.tfg.eHealth.entities;


import com.tfg.eHealth.vo.EstadoEnum;
import com.tfg.eHealth.vo.SexoEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellidos;

    @Column
    private LocalDate fechaNacimiento;

    @Column
    private String dni;

    @Column
    private String numSegSocial;

    @Column
    private String telefono;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Double peso;

    @Column
    private Double altura;

    @Column
    private SexoEnum sexo;

    @Column
    private Boolean fumador;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "historial_clinico_id", referencedColumnName = "id")
    private HistorialClinico historialClinico;

    @ManyToOne
    @JoinColumn(name = "medico_asignado_id")
    private Medico medicoAsignado;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<SolicitudConsulta> solicitudesConsulta;

}

