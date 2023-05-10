package com.tfg.eHealth.services;

import com.tfg.eHealth.entities.Medico;
import com.tfg.eHealth.repositories.MedicoRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    MedicoRepository medicoRepository;

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

    public Medico getMedicoWithLessAsignations() throws NotFoundException {
        return medicoRepository.getMedicoWithLessAsignations();
    }

    public void create(Medico toCreate) {
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
