package com.tfg.eHealth.services;

import com.tfg.eHealth.entities.Paciente;
import com.tfg.eHealth.repositories.PacienteRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    PacienteRepository pacienteRepository;

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

    public void create(Paciente toCreate) {
        pacienteRepository.save(toCreate);
    }

    public void update(Paciente toUpdate, Long id) throws NotFoundException {
        Optional<Paciente> byId = pacienteRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Paciente con id <" + id + "> no encontrado.");
        }

        toUpdate.setId(id);
        pacienteRepository.save(toUpdate);
    }

    public void deletePaciente(Long id) throws NotFoundException {
        Optional<Paciente> byId = pacienteRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Paciente con id <" + id + "> no existe.");
        }

        pacienteRepository.delete(byId.get());
    }
}
