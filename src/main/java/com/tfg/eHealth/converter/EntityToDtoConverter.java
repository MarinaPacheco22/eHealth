package com.tfg.eHealth.converter;

import com.tfg.eHealth.dtos.*;
import com.tfg.eHealth.entities.*;
import com.tfg.eHealth.vo.Archivo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityToDtoConverter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ByteToMultipartFileConverter byteToMultipartFileConverter;

    public PacienteInDto convertIn(Paciente paciente) {
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
        pacienteInDTO.setSexo(paciente.getSexo().name());
        pacienteInDTO.setFumador(paciente.getFumador());
        pacienteInDTO.setPassword(paciente.getPassword());
        return pacienteInDTO;
    }

    public PacienteOutDto convertOut(Paciente paciente) {
        PacienteOutDto pacienteOutDTO = new PacienteOutDto();
        pacienteOutDTO.setId(paciente.getId());
        pacienteOutDTO.setNombre(paciente.getNombre());
        pacienteOutDTO.setApellidos(paciente.getApellidos());
        pacienteOutDTO.setFechaNacimiento(paciente.getFechaNacimiento().getDayOfMonth() + "-" + paciente.getFechaNacimiento().getMonthValue() + "-" + paciente.getFechaNacimiento().getYear());
        pacienteOutDTO.setDni(paciente.getDni());
        pacienteOutDTO.setNumSegSocial(paciente.getNumSegSocial());
        pacienteOutDTO.setTelefono(paciente.getTelefono());
        pacienteOutDTO.setEmail(paciente.getEmail());
        pacienteOutDTO.setPeso(paciente.getPeso());
        pacienteOutDTO.setAltura(paciente.getAltura());
        pacienteOutDTO.setFumador(paciente.getFumador());
        pacienteOutDTO.setSexo(paciente.getSexo().name());
        pacienteOutDTO.setPassword(paciente.getPassword());
        pacienteOutDTO.setAlergias(paciente.getHistorialClinico().getAlergias());
        pacienteOutDTO.setEnfermedadesDiagnosticadas(paciente.getHistorialClinico().getEnfermedadesDiagnosticadas());
        pacienteOutDTO.setIntervenciones(paciente.getHistorialClinico().getIntervenciones());
        pacienteOutDTO.setMedicacionActual(paciente.getHistorialClinico().getMedicacionActual());
        if (paciente.getMedicoAsignado() != null) {
            pacienteOutDTO.setMedicoAsignado(paciente.getMedicoAsignado().getId());
        }
        return pacienteOutDTO;
    }

    public MedicoDto convertIn(Medico medico) {
        MedicoDto medicoDTO = new MedicoDto();
        medicoDTO.setId(medico.getId());
        medicoDTO.setNombre(medico.getNombre());
        medicoDTO.setApellidos(medico.getApellidos());
        medicoDTO.setFechaNacimiento(medico.getFechaNacimiento());
        medicoDTO.setSexo(medico.getSexo().name());
        medicoDTO.setDni(medico.getDni());
        medicoDTO.setTelefono(medico.getTelefono());
        medicoDTO.setEmail(medico.getEmail());
        medicoDTO.setPassword(medico.getPassword());
        medicoDTO.setNumeroDeColegiado(medico.getNumeroDeColegiado());
        medicoDTO.setEspecialidad(medico.getEspecialidad());
        medicoDTO.setActivo(medico.getActivo());
        if (medico.getPacientesAsignados() != null) {
            List<PacienteInDto> pacientesDto = medico.getPacientesAsignados().stream()
                    .map(this::convertIn)
                    .collect(Collectors.toList());
            medicoDTO.setPacientesAsignados(pacientesDto);
        }
        return medicoDTO;
    }

    public HistorialClinicoOutDto convertIn(HistorialClinico historialClinico) {
        HistorialClinicoOutDto dto = new HistorialClinicoOutDto();
        dto.setId(historialClinico.getId());
        dto.setEnfermedadesDiagnosticadas(historialClinico.getEnfermedadesDiagnosticadas());
        dto.setIntervenciones(historialClinico.getIntervenciones());
        dto.setAlergias(historialClinico.getAlergias());
        dto.setMedicacionActual(historialClinico.getMedicacionActual());
        return dto;
    }

    public PruebaMedicaOutDto convert(PruebaMedica pruebaMedica) {
        PruebaMedicaOutDto dto = new PruebaMedicaOutDto();
        dto.setId(pruebaMedica.getId());
        dto.setPrueba(pruebaMedica.getPrueba());
        dto.setFechaHoraCita(pruebaMedica.getFechaHoraCita());
        dto.setConsulta(pruebaMedica.getConsulta());
        dto.setResultadosUrl(pruebaMedica.getResultadosUrl());
        dto.setPacienteOutDto(convertOut(pruebaMedica.getSolicitudConsulta().getPaciente()));
        dto.setMedicoOutDto(convertOut(pruebaMedica.getSolicitudConsulta().getMedico()));
        dto.setSolicitudConsultaOutDto(convertOut(pruebaMedica.getSolicitudConsulta()));
        return dto;
    }

    public ResolucionConsultaInDto convert(ResolucionConsulta resolucionConsulta) {
        ResolucionConsultaInDto dto = new ResolucionConsultaInDto();
        dto.setId(resolucionConsulta.getId());
        dto.setDiagnostico(resolucionConsulta.getDiagnostico());
        dto.setTratamiento(resolucionConsulta.getTratamiento());
        dto.setObservacion(resolucionConsulta.getObservacion());
        return dto;
    }

    public SolicitudConsultaInDto convertIn(SolicitudConsulta solicitudConsulta) {
        SolicitudConsultaInDto solicitudConsultaInDto = new SolicitudConsultaInDto();
        solicitudConsultaInDto.setId(solicitudConsulta.getId());
        solicitudConsultaInDto.setDescripcion(solicitudConsulta.getDescripcion());
        solicitudConsultaInDto.setFecha(solicitudConsulta.getFecha());
        solicitudConsultaInDto.setEstado(solicitudConsulta.getEstado().name());
        if (solicitudConsulta.getArchivos() != null) {
            List<MultipartFile> archivos = solicitudConsulta.getArchivos().stream()
                    .map(archivo -> {
                        try {
                            int numeroArchivo = solicitudConsulta.getArchivos().indexOf(archivo) + 1;
                            String nombreArchivo = "archivo" + numeroArchivo;
                            return byteToMultipartFileConverter.convert(archivo, nombreArchivo);
                        } catch (IOException e) {
                            logger.error("Error al procesar el archivo: " + e.getMessage());
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
            solicitudConsultaInDto.setArchivos(archivos);
        }

        return solicitudConsultaInDto;
    }

    public SolicitudConsultaOutDto convertOut(SolicitudConsulta solicitudConsulta) {
        SolicitudConsultaOutDto solicitudConsultaOutDto = new SolicitudConsultaOutDto();
        solicitudConsultaOutDto.setId(solicitudConsulta.getId());
        solicitudConsultaOutDto.setDescripcion(solicitudConsulta.getDescripcion());
        solicitudConsultaOutDto.setFecha(solicitudConsulta.getFecha());
        solicitudConsultaOutDto.setEstado(solicitudConsulta.getEstado().name());
        solicitudConsultaOutDto.setMedicoOutDto(convertOut(solicitudConsulta.getMedico()));
        solicitudConsultaOutDto.setPacienteOutDto(convertOut(solicitudConsulta.getPaciente()));
        solicitudConsultaOutDto.setNumArchivos(solicitudConsulta.getArchivos().size());
        solicitudConsultaOutDto.setPruebasBoolean(solicitudConsulta.getPruebasMedicas().size() != 0);
        return solicitudConsultaOutDto;
    }

    public MedicoOutDto convertOut(Medico medico) {
        MedicoOutDto outDto = new MedicoOutDto();
        outDto.setId(medico.getId());
        outDto.setNombre(medico.getNombre());
        outDto.setApellidos(medico.getApellidos());
        outDto.setEspecialidad(medico.getEspecialidad());
        outDto.setSexo(medico.getSexo().name());
        return outDto;
    }

    public List<ArchivoOutDto> convert(List<Archivo> files) {
        return files.stream()
                .map(archivo -> {
                    try {
                        int numeroArchivo = files.indexOf(archivo) + 1;
                        String nombreArchivo = "archivo" + numeroArchivo;
                        MultipartFile multipartFile = byteToMultipartFileConverter.convert(archivo, nombreArchivo);
                        ArchivoOutDto outDto = new ArchivoOutDto();
                        outDto.setNombreArchivo(multipartFile.getName());
                        outDto.setTipoContenido(multipartFile.getContentType());
                        outDto.setBytes(multipartFile.getBytes());
                        return outDto;
                    } catch (IOException e) {
                        logger.error("Error al procesar el archivo: " + e.getMessage());
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }
}
