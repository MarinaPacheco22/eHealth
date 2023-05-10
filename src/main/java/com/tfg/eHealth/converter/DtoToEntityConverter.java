package com.tfg.eHealth.converter;

import com.tfg.eHealth.dtos.*;
import com.tfg.eHealth.entities.*;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoToEntityConverter {

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

    public Paciente convert(PacienteDto pacienteDto) {
        Paciente paciente = new Paciente();
        paciente.setId(pacienteDto.getId());
        paciente.setNombre(pacienteDto.getNombre());
        paciente.setApellidos(pacienteDto.getApellidos());
        paciente.setFechaNacimiento(pacienteDto.getFechaNacimiento());
        paciente.setDni(pacienteDto.getDni());
        paciente.setNumSegSocial(pacienteDto.getNumSegSocial());
        paciente.setTelefono(pacienteDto.getTelefono());
        paciente.setEmail(pacienteDto.getEmail());
        paciente.setPassword(pacienteDto.getPassword());
        paciente.setPeso(pacienteDto.getPeso());
        paciente.setAltura(pacienteDto.getAltura());
        if (pacienteDto.getHistorialClinico() != null) {
            paciente.setHistorialClinico(convert(pacienteDto.getHistorialClinico()));
        }
        if (pacienteDto.getMedicoAsignado() != null) {
            paciente.setMedicoAsignado(convert(pacienteDto.getMedicoAsignado()));
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

    public HistorialClinico convert(HistorialClinicoDto historialClinicoDto) {
        HistorialClinico historialClinico = new HistorialClinico();
        historialClinico.setId(historialClinicoDto.getId());
        historialClinico.setFechaUltimaAtencion(historialClinicoDto.getFechaUltimaAtencion());
        historialClinico.setEnfermedadesDiagnosticadas(historialClinicoDto.getEnfermedadesDiagnosticadas());
        historialClinico.setIntervenciones(historialClinicoDto.getIntervenciones());
        historialClinico.setAlergias(historialClinicoDto.getAlergias());
        historialClinico.setMedicacionActual(historialClinicoDto.getMedicacionActual());
        if (historialClinicoDto.getPruebasMedicas() != null) {
            List<PruebaMedica> pruebas = historialClinicoDto.getPruebasMedicas().stream()
                    .map(this::convert)
                    .collect(Collectors.toList());
            historialClinico.setPruebasMedicas(pruebas);
        }
        historialClinico.setPaciente(convert(historialClinicoDto.getPaciente()));
        return historialClinico;
    }
}
