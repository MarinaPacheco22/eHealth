package com.tfg.eHealth.converter;

import com.tfg.eHealth.dtos.HistorialClinicoDto;
import com.tfg.eHealth.dtos.MedicoDto;
import com.tfg.eHealth.dtos.PacienteDto;
import com.tfg.eHealth.dtos.PruebaMedicaDto;
import com.tfg.eHealth.entities.HistorialClinico;
import com.tfg.eHealth.entities.Medico;
import com.tfg.eHealth.entities.Paciente;
import com.tfg.eHealth.entities.PruebaMedica;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class entityToDtoConverter {

    public PacienteDto convert(Paciente paciente) {
        PacienteDto pacienteDTO = new PacienteDto();
        pacienteDTO.setId(paciente.getId());
        pacienteDTO.setNombre(paciente.getNombre());
        pacienteDTO.setApellidos(paciente.getApellidos());
        pacienteDTO.setDni(paciente.getDni());
        pacienteDTO.setNumSegSocial(paciente.getNumSegSocial());
        pacienteDTO.setTelefono(paciente.getTelefono());
        pacienteDTO.setEmail(paciente.getEmail());
        pacienteDTO.setIdioma(paciente.getIdioma());
        pacienteDTO.setPeso(paciente.getPeso());
        pacienteDTO.setAltura(paciente.getAltura());
        pacienteDTO.setHistorialClinico(convert(paciente.getHistorialClinico()));
        return pacienteDTO;
    }

    public MedicoDto convert(Medico medico) {
        MedicoDto medicoDTO = new MedicoDto();
        medicoDTO.setId(medico.getId());
        medicoDTO.setNombre(medico.getNombre());
        medicoDTO.setApellidos(medico.getApellidos());
        medicoDTO.setDni(medico.getDni());
        medicoDTO.setTelefono(medico.getTelefono());
        medicoDTO.setEmail(medico.getEmail());
        medicoDTO.setNumeroDeColegiado(medico.getNumeroDeColegiado());
        medicoDTO.setEspecialidad(medico.getEspecialidad());
        medicoDTO.setIdiomas(medico.getIdiomas());
        medicoDTO.setActivo(medico.isActivo());
        return medicoDTO;
    }

    public HistorialClinicoDto convert(HistorialClinico historialClinico) {
        HistorialClinicoDto dto = new HistorialClinicoDto();
        dto.setId(historialClinico.getId());
        dto.setFechaUltimaAtencion(historialClinico.getFechaUltimaAtencion());
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
