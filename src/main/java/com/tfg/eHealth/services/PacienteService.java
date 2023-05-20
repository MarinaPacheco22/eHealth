package com.tfg.eHealth.services;

import com.tfg.eHealth.converter.DtoToEntityConverter;
import com.tfg.eHealth.dtos.PacienteOutDto;
import com.tfg.eHealth.entities.HistorialClinico;
import com.tfg.eHealth.entities.Medico;
import com.tfg.eHealth.entities.Paciente;
import com.tfg.eHealth.repositories.HistorialClinicoRepository;
import com.tfg.eHealth.repositories.MedicoRepository;
import com.tfg.eHealth.repositories.PacienteRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    HistorialClinicoRepository historialClinicoRepository;

    @Autowired
    DtoToEntityConverter dtoToEntityConverter;

    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente getPacienteById(Long id) throws NotFoundException {
        Optional<Paciente> byId = pacienteRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Paciente con id <" + id + "> no encontrado.");
        }

        return byId.get();
    }

    public Paciente getPacienteByEmail(String email) throws NotFoundException {
        Optional<Paciente> byEmail = pacienteRepository.findByEmail(email);

        if (byEmail.isEmpty()) {
            throw new NotFoundException("Usuario con email <" + email + "> no encontrado.");
        }

        return byEmail.get();
    }

    public void create(PacienteOutDto outDto) {
        String email = outDto.getEmail();
        Optional<Medico> medicoByEmail= medicoRepository.findByEmail(email);
        Optional<Paciente> pacienteByEmail = pacienteRepository.findByEmail(email);
        if (medicoByEmail.isPresent() || pacienteByEmail.isPresent()) {
            throw new DuplicateKeyException("Este email ya está registrado.");
        }
        Paciente paciente = dtoToEntityConverter.convert(outDto);
        Medico medicoAsignado = medicoRepository.getMedicoFamiliarWithLessAsignations();
        paciente.setMedicoAsignado(medicoAsignado);
        Paciente pacienteCreated = pacienteRepository.save(paciente);
        HistorialClinico historialClinico = dtoToEntityConverter.convertToHistorialClinico(outDto);
        historialClinico.setPaciente(pacienteCreated);
        HistorialClinico historialClinicoCreated = historialClinicoRepository.save(historialClinico);
        pacienteCreated.setHistorialClinico(historialClinicoCreated);
        pacienteRepository.save(pacienteCreated);
    }

    public Paciente update(Paciente toUpdate, Long id) throws NotFoundException {
        Optional<Paciente> byId = pacienteRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Paciente con id <" + id + "> no encontrado.");
        }

        toUpdate.setId(id);
        return pacienteRepository.save(toUpdate);
    }

    public void deletePaciente(Long id) throws NotFoundException {
        Optional<Paciente> byId = pacienteRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Paciente con id <" + id + "> no existe.");
        }

        pacienteRepository.delete(byId.get());
    }

    public List<Paciente> getPacienteByMedicoId(Long id) throws NotFoundException {
        Optional<Medico> medico = medicoRepository.findById(id);

        if (medico.isEmpty()) {
            throw new NotFoundException("Medico con id <" + id + "> no existe.");
        }

        return pacienteRepository.findByMedicoAsignado(medico.get());
    }
}
