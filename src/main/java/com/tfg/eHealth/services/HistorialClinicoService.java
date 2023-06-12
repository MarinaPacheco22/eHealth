package com.tfg.eHealth.services;

import com.tfg.eHealth.converter.DtoToEntityConverter;
import com.tfg.eHealth.converter.EntityToDtoConverter;
import com.tfg.eHealth.dtos.HistorialClinicoInDto;
import com.tfg.eHealth.dtos.HistorialClinicoOutDto;
import com.tfg.eHealth.dtos.PacienteInDto;
import com.tfg.eHealth.entities.HistorialClinico;
import com.tfg.eHealth.entities.Paciente;
import com.tfg.eHealth.repositories.HistorialClinicoRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class HistorialClinicoService {

    @Autowired
    HistorialClinicoRepository historialClinicoRepository;

    @Autowired
    PacienteService pacienteService;

    @Autowired
    EntityToDtoConverter entityToDtoConverter;

    @Autowired
    DtoToEntityConverter dtoToEntityConverter;

    @PersistenceContext
    private EntityManager entityManager;

    public List<HistorialClinico> getAllHistorialesClinicos() {
        return historialClinicoRepository.findAll();
    }

    public HistorialClinico getHistorialClinicoById(Long id) throws NotFoundException {
        Optional<HistorialClinico> byId = historialClinicoRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Historial clinico con id <" + id + "> no encontrado.");
        }

        return byId.get();
    }

    public void create(HistorialClinicoOutDto outDto) throws NotFoundException {
        Paciente pacienteByID = pacienteService.getPacienteById(outDto.getPacienteId());
        PacienteInDto pacienteInDto = entityToDtoConverter.convertIn(pacienteByID);
        HistorialClinicoInDto inDto = convert(outDto);
        inDto.setPaciente(pacienteInDto);
        HistorialClinico toCreate = dtoToEntityConverter.convert(inDto);

        historialClinicoRepository.save(toCreate);
    }

    @Transactional
    public void update(HistorialClinico toUpdate) throws NotFoundException {
        HistorialClinico byId = historialClinicoRepository.getById(toUpdate.getId());
        byId.setAlergias(toUpdate.getAlergias());
        byId.setIntervenciones(toUpdate.getIntervenciones());
        byId.setEnfermedadesDiagnosticadas(toUpdate.getEnfermedadesDiagnosticadas());
        byId.setMedicacionActual(toUpdate.getMedicacionActual());
        entityManager.merge(toUpdate);
    }

    public void deleteHistorialClinico(Long id) throws NotFoundException {
        Optional<HistorialClinico> byId = historialClinicoRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Historial clinico con id <" + id + "> no existe.");
        }

        historialClinicoRepository.delete(byId.get());
    }

    private HistorialClinicoInDto convert(HistorialClinicoOutDto outDto) {
        HistorialClinicoInDto inDto = new HistorialClinicoInDto();
        inDto.setId(outDto.getId());
        inDto.setAlergias(outDto.getAlergias());
        inDto.setIntervenciones(outDto.getIntervenciones());
        inDto.setEnfermedadesDiagnosticadas(outDto.getEnfermedadesDiagnosticadas());
        return inDto;
    }

    public HistorialClinico getHistorialClinicoByPacienteId(Long id) throws NotFoundException {
        Optional<HistorialClinico> byId = historialClinicoRepository.findByPaciente_Id(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("No hay historial clínico asociado al paciente con id <" + id + ">.");
        }

        return byId.get();
    }
}
