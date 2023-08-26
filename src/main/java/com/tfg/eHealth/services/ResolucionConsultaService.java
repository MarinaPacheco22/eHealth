package com.tfg.eHealth.services;

import com.tfg.eHealth.entities.ResolucionConsulta;
import com.tfg.eHealth.entities.SolicitudConsulta;
import com.tfg.eHealth.repositories.ResolucionConsultaRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResolucionConsultaService {

    @Autowired
    ResolucionConsultaRepository resolucionConsultaRepository;

    @Autowired
    SolicitudConsultaService solicitudConsultaService;

    public List<ResolucionConsulta> getAllResolucionesConsulta() {
        return resolucionConsultaRepository.findAll();
    }

    public ResolucionConsulta getResolucionConsultaById(Long id) throws NotFoundException {
        Optional<ResolucionConsulta> byId = resolucionConsultaRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Resolución de consulta con id <" + id + "> no encontrado.");
        }

        return byId.get();
    }

    public void create(ResolucionConsulta toCreate, Long solicitudConsultaId) throws NotFoundException {
        SolicitudConsulta solicitudConsultaById = solicitudConsultaService.getSolicitudConsultaById(solicitudConsultaId);
        toCreate.setSolicitudConsulta(solicitudConsultaById);
        resolucionConsultaRepository.save(toCreate);
    }

    public void update(ResolucionConsulta toUpdate, Long id) throws NotFoundException {
        Optional<ResolucionConsulta> byId = resolucionConsultaRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Resolución de consulta con id <" + id + "> no encontrado.");
        }

        toUpdate.setId(id);
        resolucionConsultaRepository.save(toUpdate);
    }

    public void deleteResolucionConsulta(Long id) throws NotFoundException {
        Optional<ResolucionConsulta> byId = resolucionConsultaRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Resolución de consulta con id <" + id + "> no existe.");
        }

        resolucionConsultaRepository.delete(byId.get());
    }

    public ResolucionConsulta getResolucionByConsultaId(Long id) throws NotFoundException {
        List<ResolucionConsulta> byConsultaId = resolucionConsultaRepository.findAllBySolicitudConsulta_Id(id);
        
        if (byConsultaId.isEmpty()) {
            throw new NotFoundException("Resolución de consulta con  id <" + id + "> no existe.");
        }

        return byConsultaId.get(0);
    }
}
