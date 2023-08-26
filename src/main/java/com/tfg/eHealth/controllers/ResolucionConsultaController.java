package com.tfg.eHealth.controllers;

import com.tfg.eHealth.converter.DtoToEntityConverter;
import com.tfg.eHealth.converter.EntityToDtoConverter;
import com.tfg.eHealth.dtos.ResolucionConsultaInDto;
import com.tfg.eHealth.entities.ResolucionConsulta;
import com.tfg.eHealth.services.HistorialClinicoService;
import com.tfg.eHealth.services.PacienteService;
import com.tfg.eHealth.services.ResolucionConsultaService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/resolucion-consulta")
public class ResolucionConsultaController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResolucionConsultaService resolucionConsultaService;

    @Autowired
    private HistorialClinicoService historialClinicoService;

    @Autowired
    private EntityToDtoConverter entityToDtoConverter;

    @Autowired
    private DtoToEntityConverter dtoToEntityConverter;

    @GetMapping
    public ResponseEntity<?> getResolucionConsultaList() {
        ResponseEntity<?> toReturn;
        List<ResolucionConsulta> pruebasMedicas = resolucionConsultaService.getAllResolucionesConsulta();
        if (pruebasMedicas.isEmpty()) {
            toReturn = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<ResolucionConsultaInDto> appRes = pruebasMedicas.stream()
                    .map(entityToDtoConverter::convert)
                    .collect(Collectors.toList());
            toReturn = new ResponseEntity<>(appRes, HttpStatus.OK);
        }
        return toReturn;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResolucionConsultaById(@PathVariable Long id) {
        ResponseEntity<?> toReturn;
        try {
            ResolucionConsulta resolucionConsulta = resolucionConsultaService.getResolucionConsultaById(id);
            ResolucionConsultaInDto appRes = entityToDtoConverter.convert(resolucionConsulta);
            toReturn = new ResponseEntity<>(appRes, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.warn(e.getMessage(), e);
            toReturn = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            toReturn = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return toReturn;
    }

    @GetMapping("/by-consulta/{id}")
    public ResponseEntity<?> getResolucionConsultaByConsultaId(@PathVariable Long id) {
        ResponseEntity<?> toReturn;
        try {
            ResolucionConsulta resolucionConsulta = resolucionConsultaService.getResolucionByConsultaId(id);
            ResolucionConsultaInDto appRes = entityToDtoConverter.convert(resolucionConsulta);
            toReturn = new ResponseEntity<>(appRes, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.warn(e.getMessage(), e);
            toReturn = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            toReturn = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return toReturn;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ResolucionConsultaInDto resolucionConsultaInDto) {
        ResponseEntity<?> toReturn;
        try {
            if (resolucionConsultaInDto.getMedicacion() != null || resolucionConsultaInDto.getEnfermedad() != null || resolucionConsultaInDto.getAlergia() != null) {
                historialClinicoService.updateHistorialCLinico(resolucionConsultaInDto.getMedicacion(), resolucionConsultaInDto.getEnfermedad(), resolucionConsultaInDto.getAlergia(), resolucionConsultaInDto.getPacienteId());
            }
            ResolucionConsulta resolucionConsulta = dtoToEntityConverter.convert(resolucionConsultaInDto);
            resolucionConsultaService.create(resolucionConsulta, resolucionConsultaInDto.getSolicitudConsultaId());
            toReturn = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage(), e);
            toReturn = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            toReturn = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return toReturn;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ResolucionConsultaInDto resolucionConsultaInDto) {
        ResponseEntity<?> toReturn;
        try {
            ResolucionConsulta resolucionConsulta = dtoToEntityConverter.convert(resolucionConsultaInDto);
            resolucionConsultaService.update(resolucionConsulta, id);
            toReturn = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NotFoundException e) {
            logger.warn(e.getMessage(), e);
            toReturn = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage(), e);
            toReturn = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            toReturn = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return toReturn;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ResponseEntity<?> toReturn;
        try {
            resolucionConsultaService.deleteResolucionConsulta(id);
            toReturn = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NotFoundException e) {
            logger.warn(e.getMessage(), e);
            toReturn = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            toReturn = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return toReturn;
    }

}

