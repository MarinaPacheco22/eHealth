package com.tfg.eHealth.services;

import com.tfg.eHealth.converter.DtoToEntityConverter;
import com.tfg.eHealth.converter.EntityToDtoConverter;
import com.tfg.eHealth.entities.Medico;
import com.tfg.eHealth.entities.Paciente;
import com.tfg.eHealth.entities.SolicitudConsulta;
import com.tfg.eHealth.repositories.MedicoRepository;
import com.tfg.eHealth.repositories.PacienteRepository;
import com.tfg.eHealth.repositories.SolicitudConsultaRepository;
import com.tfg.eHealth.vo.EstadoEnum;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolicitudConsultaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SolicitudConsultaRepository solicitudConsultaRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    EntityToDtoConverter entityToDtoConverter;

    @Autowired
    DtoToEntityConverter dtoToEntityConverter;

    public List<SolicitudConsulta> getAllSolicitudesConsulta() {
        return solicitudConsultaRepository.findAll();
    }

    public SolicitudConsulta getSolicitudConsultaById(Long id) throws NotFoundException {
        Optional<SolicitudConsulta> byId = solicitudConsultaRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Solicitud de consulta con id <" + id + "> no encontrado.");
        }

        return byId.get();
    }

    public void create(MultipartFile[] files, String descripcion, String pacienteIdStr, String medicoIdStr) throws NotFoundException {
        Optional<Paciente> paciente = pacienteRepository.findById(Long.parseLong(pacienteIdStr));
        if (paciente.isEmpty()) {
            throw new NotFoundException("Paciente con id <" + Long.parseLong(pacienteIdStr) + "> no encontrado.");
        }

        Optional<Medico> medico = medicoRepository.findById(Long.parseLong(medicoIdStr));
        if (medico.isEmpty()) {
            throw new NotFoundException("Medico con id <" + Long.parseLong(medicoIdStr) + "> no encontrado.");
        }
        SolicitudConsulta toCreate = new SolicitudConsulta();
        toCreate.setDescripcion(descripcion);
        toCreate.setPaciente(paciente.get());
        toCreate.setMedico(medico.get());
        toCreate.setFecha(LocalDate.now());
        toCreate.setEstado(EstadoEnum.SOLICITUD_ENVIADA);
        if (files != null) {
            List<byte[]> archivos = Arrays.stream(files)
                    .map(archivo -> {
                        try {
                            return archivo.getBytes();
                        } catch (IOException e) {
                            logger.error("Error al procesar el archivo." + e.getMessage());
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
            toCreate.setArchivos(archivos);
        }
        solicitudConsultaRepository.save(toCreate);
    }

    public void update(SolicitudConsulta toUpdate, Long id) throws NotFoundException {
        Optional<SolicitudConsulta> byId = solicitudConsultaRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Solicitud de consulta con id <" + id + "> no encontrado.");
        }

        toUpdate.setId(id);
        solicitudConsultaRepository.save(toUpdate);
    }

    public void deleteSolicitudConsulta(Long id) throws NotFoundException {
        Optional<SolicitudConsulta> byId = solicitudConsultaRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Solicitud de consulta con id <" + id + "> no existe.");
        }

        solicitudConsultaRepository.delete(byId.get());
    }

    public List<SolicitudConsulta> getSolicitudesConsultaByPacienteId(Long id) {
        return solicitudConsultaRepository.findAllByPaciente_Id(id);
    }
}