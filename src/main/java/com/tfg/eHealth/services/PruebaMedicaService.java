package com.tfg.eHealth.services;

import com.tfg.eHealth.entities.PruebaMedica;
import com.tfg.eHealth.entities.SolicitudConsulta;
import com.tfg.eHealth.repositories.PruebaMedicaRepository;
import com.tfg.eHealth.repositories.SolicitudConsultaRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PruebaMedicaService {

    @Autowired
    PruebaMedicaRepository pruebaMedicaRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    SolicitudConsultaService solicitudConsultaService;

    public List<PruebaMedica> getAllPruebasMedicas() {
        return pruebaMedicaRepository.findAll();
    }

    public PruebaMedica getPruebaMedicaById(Long id) throws NotFoundException {
        Optional<PruebaMedica> byId = pruebaMedicaRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Prueba médica con id <" + id + "> no encontrado.");
        }

        return byId.get();
    }

    public void create(PruebaMedica toCreate, Long solicitudId) throws NotFoundException {
        SolicitudConsulta solicitudConsultaById = solicitudConsultaService.getSolicitudConsultaById(solicitudId);
        toCreate.setSolicitudConsulta(solicitudConsultaById);
        pruebaMedicaRepository.save(toCreate);
    }

    public void update(PruebaMedica toUpdate, Long id) throws NotFoundException {
        Optional<PruebaMedica> byId = pruebaMedicaRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Prueba médica con id <" + id + "> no encontrado.");
        }

        toUpdate.setId(id);
        pruebaMedicaRepository.save(toUpdate);
    }

    public void deletePruebaMedica(Long id) throws NotFoundException {
        Optional<PruebaMedica> byId = pruebaMedicaRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Prueba médica con id <" + id + "> no existe.");
        }

        pruebaMedicaRepository.delete(byId.get());
    }

    public List<PruebaMedica> getWithoutResultPruebasMedicasByMedico(Long id) {
        return pruebaMedicaRepository.findAllByResultadosUrlIsNullAndSolicitudConsulta_Medico_Id(id);
    }

    public List<PruebaMedica> getPruebasMedicasByPaciente(Long id) {
        return pruebaMedicaRepository.findAllBySolicitudConsulta_Paciente_Id(id);
    }

    public List<PruebaMedica> getPruebasMedicasBySolicitud(Long id) {
        return pruebaMedicaRepository.findAllBySolicitudConsulta_Id(id);
    }

    @Transactional
    public void addResultsUrl(String resultsUrl, Long id) throws NotFoundException {
        PruebaMedica byId = getPruebaMedicaById(id);
        byId.setResultadosUrl(resultsUrl);
        entityManager.merge(byId);
    }
}
