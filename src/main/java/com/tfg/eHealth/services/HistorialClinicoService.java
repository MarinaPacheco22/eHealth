package com.tfg.eHealth.services;

import com.tfg.eHealth.entities.HistorialClinico;
import com.tfg.eHealth.repositories.HistorialClinicoRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialClinicoService {

    @Autowired
    HistorialClinicoRepository historialClinicoRepository;

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

    public void create(HistorialClinico toCreate) {
        historialClinicoRepository.save(toCreate);
    }

    public void update(HistorialClinico toUpdate, Long id) throws NotFoundException {
        Optional<HistorialClinico> byId = historialClinicoRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Historial clinico con id <" + id + "> no encontrado.");
        }

        toUpdate.setId(id);
        historialClinicoRepository.save(toUpdate);
    }

    public void deleteHistorialClinico(Long id) throws NotFoundException {
        Optional<HistorialClinico> byId = historialClinicoRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Historial clinico con id <" + id + "> no existe.");
        }

        historialClinicoRepository.delete(byId.get());
    }
}
