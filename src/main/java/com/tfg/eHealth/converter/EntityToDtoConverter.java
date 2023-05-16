package com.tfg.eHealth.converter;

import com.tfg.eHealth.dtos.HistorialClinicoOutDto;
import com.tfg.eHealth.dtos.MedicoDto;
import com.tfg.eHealth.dtos.PacienteInDto;
import com.tfg.eHealth.dtos.PruebaMedicaDto;
import com.tfg.eHealth.entities.HistorialClinico;
import com.tfg.eHealth.entities.Medico;
import com.tfg.eHealth.entities.Paciente;
import com.tfg.eHealth.entities.PruebaMedica;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityToDtoConverter {

    public PacienteInDto convert(Paciente paciente) {
        PacienteInDto pacienteInDTO = new PacienteInDto();
        pacienteInDTO.setId(paciente.getId());
        pacienteInDTO.setNombre(paciente.getNombre());
        pacienteInDTO.setApellidos(paciente.getApellidos());
        pacienteInDTO.setFechaNacimiento(paciente.getFechaNacimiento());
        pacienteInDTO.setDni(paciente.getDni());
        pacienteInDTO.setNumSegSocial(paciente.getNumSegSocial());
        pacienteInDTO.setTelefono(paciente.getTelefono());
        pacienteInDTO.setEmail(paciente.getEmail());
        pacienteInDTO.setPeso(paciente.getPeso());
        pacienteInDTO.setAltura(paciente.getAltura());
        return pacienteInDTO;
    }

    public MedicoDto convert(Medico medico) {
        MedicoDto medicoDTO = new MedicoDto();
        medicoDTO.setId(medico.getId());
        medicoDTO.setNombre(medico.getNombre());
        medicoDTO.setApellidos(medico.getApellidos());
        medicoDTO.setFechaNacimiento(medico.getFechaNacimiento());
        medicoDTO.setDni(medico.getDni());
        medicoDTO.setTelefono(medico.getTelefono());
        medicoDTO.setEmail(medico.getEmail());
        medicoDTO.setNumeroDeColegiado(medico.getNumeroDeColegiado());
        medicoDTO.setEspecialidad(medico.getEspecialidad());
        medicoDTO.setActivo(medico.isActivo());
        if (medico.getPacientesAsignados() != null) {
            List<PacienteInDto> pacientesDto = medico.getPacientesAsignados().stream()
                    .map(this::convert)
                    .collect(Collectors.toList());
            medicoDTO.setPacientesAsignados(pacientesDto);
        }
        return medicoDTO;
    }

    public HistorialClinicoOutDto convert(HistorialClinico historialClinico) {
        HistorialClinicoOutDto dto = new HistorialClinicoOutDto();
        dto.setId(historialClinico.getId());
        dto.setEnfermedadesDiagnosticadas(historialClinico.getEnfermedadesDiagnosticadas());
        dto.setIntervenciones(historialClinico.getIntervenciones());
        dto.setAlergias(historialClinico.getAlergias());
        dto.setMedicacionActual(historialClinico.getMedicacionActual());
        if (historialClinico.getPruebasMedicas() != null) {
            List<PruebaMedicaDto> pruebasDto = historialClinico.getPruebasMedicas().stream()
                    .map(this::convert)
                    .collect(Collectors.toList());
            dto.setPruebasMedicas(pruebasDto);
        }
        return dto;
    }

    public PruebaMedicaDto convert(PruebaMedica pruebaMedica) {
        PruebaMedicaDto dto = new PruebaMedicaDto();
        dto.setId(pruebaMedica.getId());
        dto.setFecha(pruebaMedica.getFecha());
        dto.setNombre(pruebaMedica.getNombre());
        dto.setDiagnostico(pruebaMedica.getDiagnostico());
        dto.setTratamiento(pruebaMedica.getTratamiento());
        return dto;
    }
}
