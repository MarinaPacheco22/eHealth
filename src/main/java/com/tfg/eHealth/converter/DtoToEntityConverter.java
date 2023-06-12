package com.tfg.eHealth.converter;

import com.tfg.eHealth.dtos.*;
import com.tfg.eHealth.entities.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoToEntityConverter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Medico convert(MedicoDto medicoDto) {
        Medico medico = new Medico();
        medico.setId(medicoDto.getId());
        medico.setNombre(medicoDto.getNombre());
        medico.setApellidos(medicoDto.getApellidos());
        medico.setFechaNacimiento(medicoDto.getFechaNacimiento());
        medico.setDni(medicoDto.getDni());
        medico.setTelefono(medicoDto.getTelefono());
        medico.setEmail(medicoDto.getEmail());
        medico.setPassword(medicoDto.getPassword());
        medico.setNumeroDeColegiado(medicoDto.getNumeroDeColegiado());
        medico.setEspecialidad(medicoDto.getEspecialidad());
        medico.setActivo(medicoDto.isActivo());
        if (medicoDto.getPacientesAsignados() != null) {
            List<Paciente> pacientes = medicoDto.getPacientesAsignados().stream()
                    .map(this::convert)
                    .collect(Collectors.toList());
            medico.setPacientesAsignados(pacientes);
        }
        return medico;
    }

    public Paciente convert(PacienteInDto pacienteInDto) {
        Paciente paciente = new Paciente();
        paciente.setId(pacienteInDto.getId());
        paciente.setNombre(pacienteInDto.getNombre());
        paciente.setApellidos(pacienteInDto.getApellidos());
        paciente.setFechaNacimiento(pacienteInDto.getFechaNacimiento());
        paciente.setDni(pacienteInDto.getDni());
        paciente.setNumSegSocial(pacienteInDto.getNumSegSocial());
        paciente.setTelefono(pacienteInDto.getTelefono());
        paciente.setEmail(pacienteInDto.getEmail());
        paciente.setPassword(pacienteInDto.getPassword());
        paciente.setPeso(pacienteInDto.getPeso());
        paciente.setAltura(pacienteInDto.getAltura());
        if (pacienteInDto.getHistorialClinico() != null) {
            paciente.setHistorialClinico(convert(pacienteInDto.getHistorialClinico()));
        }
        if (pacienteInDto.getMedicoAsignado() != null) {
            paciente.setMedicoAsignado(convert(pacienteInDto.getMedicoAsignado()));
        }
        return paciente;
    }

    public PruebaMedica convert(PruebaMedicaDto pruebaMedicaDto) {
        PruebaMedica pruebaMedica = new PruebaMedica();
        pruebaMedica.setId(pruebaMedicaDto.getId());
        pruebaMedica.setFecha(pruebaMedicaDto.getFecha());
        pruebaMedica.setNombre(pruebaMedicaDto.getNombre());
        pruebaMedica.setDiagnostico(pruebaMedicaDto.getDiagnostico());
        pruebaMedica.setTratamiento(pruebaMedicaDto.getTratamiento());
        pruebaMedica.setHistorialClinico(convert(pruebaMedicaDto.getHistorialClinico()));
        return pruebaMedica;
    }

    public HistorialClinico convert(HistorialClinicoInDto historialClinicoInDto) {
        HistorialClinico historialClinico = new HistorialClinico();
        historialClinico.setId(historialClinicoInDto.getId());
        historialClinico.setEnfermedadesDiagnosticadas(historialClinicoInDto.getEnfermedadesDiagnosticadas());
        historialClinico.setIntervenciones(historialClinicoInDto.getIntervenciones());
        historialClinico.setAlergias(historialClinicoInDto.getAlergias());
        historialClinico.setMedicacionActual(historialClinicoInDto.getMedicacionActual());
        return historialClinico;
    }

    public HistorialClinico convert(HistorialClinicoOutDto historialClinicoOutDto) {
        HistorialClinico historialClinico = new HistorialClinico();
        historialClinico.setId(historialClinicoOutDto.getId());
        historialClinico.setEnfermedadesDiagnosticadas(historialClinicoOutDto.getEnfermedadesDiagnosticadas());
        historialClinico.setIntervenciones(historialClinicoOutDto.getIntervenciones());
        historialClinico.setAlergias(historialClinicoOutDto.getAlergias());
        historialClinico.setMedicacionActual(historialClinicoOutDto.getMedicacionActual());
        return historialClinico;
    }

    public SolicitudConsulta convert(SolicitudConsultaInDto solicitudConsultaInDto) {
        SolicitudConsulta solicitudConsulta = new SolicitudConsulta();
        solicitudConsulta.setId(solicitudConsultaInDto.getId());
        solicitudConsulta.setDescripcion(solicitudConsultaInDto.getDescripcion());
        solicitudConsulta.setFecha(solicitudConsultaInDto.getFecha());
        if (solicitudConsultaInDto.getArchivos() != null) {
            List<byte[]> archivos = solicitudConsultaInDto.getArchivos().stream()
                    .map(archivo -> {
                        try {
                            return archivo.getBytes();
                        } catch (IOException e) {
                            logger.error("Error al procesar el archivo." + e.getMessage());
                            return null;
                        }
                    })
                    .collect(Collectors.toList());

            solicitudConsulta.setArchivos(archivos);
        }
        return solicitudConsulta;
    }

    public Paciente convert(PacienteOutDto pacienteOutDto) {
        Paciente paciente = new Paciente();
        paciente.setId(pacienteOutDto.getId());
        paciente.setNombre(pacienteOutDto.getNombre());
        paciente.setApellidos(pacienteOutDto.getApellidos());
        paciente.setFechaNacimiento(pacienteOutDto.getFechaNacimiento());
        paciente.setDni(pacienteOutDto.getDni());
        paciente.setNumSegSocial(pacienteOutDto.getNumSegSocial());
        paciente.setTelefono(pacienteOutDto.getTelefono());
        paciente.setEmail(pacienteOutDto.getEmail());
        paciente.setPassword(pacienteOutDto.getPassword());
        paciente.setPeso(pacienteOutDto.getPeso());
        paciente.setAltura(pacienteOutDto.getAltura());
        return paciente;
    }

    public HistorialClinico convertToHistorialClinico(PacienteOutDto pacienteOutDto) {
        HistorialClinico historialClinico = new HistorialClinico();
        historialClinico.setEnfermedadesDiagnosticadas(pacienteOutDto.getEnfermedadesDiagnosticadas());
        historialClinico.setIntervenciones(pacienteOutDto.getIntervenciones());
        historialClinico.setAlergias(pacienteOutDto.getAlergias());
        return historialClinico;
    }
}

