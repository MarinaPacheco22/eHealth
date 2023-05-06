package com.tfg.eHealth.services;

import com.tfg.eHealth.entities.PruebaMedica;
import com.tfg.eHealth.repositories.PruebaMedicaRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PruebaMedicaService {

    @Autowired
    PruebaMedicaRepository pruebaMedicaRepository;

    public List<PruebaMedica> getAllPruebasMedicas() {
        return pruebaMedicaRepository.findAll();
    }

    public PruebaMedica getPruebaMedicaById(Long id) throws NotFoundException {
        Optional<PruebaMedica> byId = pruebaMedicaRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Prueba medica con id <" + id + "> no encontrado.");
        }

        return byId.get();
    }

    public void create(PruebaMedica toCreate) {
        pruebaMedicaRepository.save(toCreate);
    }

    public void update(PruebaMedica toUpdate, Long id) throws NotFoundException {
        Optional<PruebaMedica> byId = pruebaMedicaRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Prueba medica con id <" + id + "> no encontrado.");
        }

        toUpdate.setId(id);
        pruebaMedicaRepository.save(toUpdate);
    }

    public void deletePruebaMedica(Long id) throws NotFoundException {
        Optional<PruebaMedica> byId = pruebaMedicaRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Prueba medica con id <" + id + "> no existe.");
        }

        pruebaMedicaRepository.delete(byId.get());
    }
}
