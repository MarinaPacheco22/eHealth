package com.tfg.eHealth.services;

import com.tfg.eHealth.entities.Medico;
import com.tfg.eHealth.entities.Paciente;
import com.tfg.eHealth.repositories.MedicoRepository;
import com.tfg.eHealth.repositories.PacienteRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    public List<Medico> getAllMedicos() {
        return medicoRepository.findAll();
    }

    public Medico getMedicoById(Long id) throws NotFoundException {
        Optional<Medico> byId = medicoRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Medico con id <" + id + "> no encontrado.");
        }

        return byId.get();
    }

    public Medico getMedicoByEmail(String email) throws NotFoundException {
        Optional<Medico> byEmail = medicoRepository.findByEmail(email);

        if (byEmail.isEmpty()) {
            throw new NotFoundException("Usuario con email <" + email + "> no encontrado.");
        }

        return byEmail.get();
    }

    public Medico getMedicoWithLessAsignations() throws NotFoundException {
        return medicoRepository.getMedicoFamiliarWithLessAsignations();
    }

    public void create(Medico toCreate) {
        String email = toCreate.getEmail();
        Optional<Medico> medicoByEmail= medicoRepository.findByEmail(email);
        Optional<Paciente> pacienteByEmail = pacienteRepository.findByEmail(email);
        if (medicoByEmail.isPresent() || pacienteByEmail.isPresent()) {
            throw new DuplicateKeyException("Este email ya está registrado.");
        }
        medicoRepository.save(toCreate);
    }

    public void update(Medico toUpdate, Long id) throws NotFoundException {
        Optional<Medico> byId = medicoRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Medico con id <" + id + "> no encontrado.");
        }

        toUpdate.setId(id);
        medicoRepository.save(toUpdate);
    }

    public void deleteMedico(Long id) throws NotFoundException {
        Optional<Medico> byId = medicoRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Medico con id <" + id + "> no existe.");
        }

        medicoRepository.delete(byId.get());
    }
}
