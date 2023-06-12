package com.tfg.eHealth.controllers;

import com.tfg.eHealth.converter.DtoToEntityConverter;
import com.tfg.eHealth.converter.EntityToDtoConverter;
import com.tfg.eHealth.dtos.HistorialClinicoInDto;
import com.tfg.eHealth.dtos.HistorialClinicoOutDto;
import com.tfg.eHealth.entities.HistorialClinico;
import com.tfg.eHealth.services.HistorialClinicoService;
import com.tfg.eHealth.services.PacienteService;
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
@RequestMapping("/historial-clinico")
public class HistorialClinicoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HistorialClinicoService historialClinicoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private EntityToDtoConverter entityToDtoConverter;

    @Autowired
    private DtoToEntityConverter dtoToEntityConverter;

    @GetMapping
    public ResponseEntity<?> getHistorialClinicoList() {
        ResponseEntity<?> toReturn;
        List<HistorialClinico> historialesClinicos = historialClinicoService.getAllHistorialesClinicos();
        if (historialesClinicos.isEmpty()) {
            toReturn = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<HistorialClinicoOutDto> appRes = historialesClinicos.stream()
                    .map(entityToDtoConverter::convertIn)
                    .collect(Collectors.toList());
            toReturn = new ResponseEntity<>(appRes, HttpStatus.OK);
        }
        return toReturn;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHistorialClinicoById(@PathVariable Long id) {
        ResponseEntity<?> toReturn;
        try {
            HistorialClinico historialClinico = historialClinicoService.getHistorialClinicoById(id);
            HistorialClinicoOutDto appRes = entityToDtoConverter.convertIn(historialClinico);
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

    @GetMapping("/by-paciente/{id}")
    public ResponseEntity<?> getHistorialClinicoByPacienteId(@PathVariable Long id) {
        ResponseEntity<?> toReturn;
        try {
            HistorialClinico historialClinico = historialClinicoService.getHistorialClinicoByPacienteId(id);
            HistorialClinicoOutDto appRes = entityToDtoConverter.convertIn(historialClinico);
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
    public ResponseEntity<?> create(@RequestBody HistorialClinicoOutDto historialClinicoOutDto) {
        ResponseEntity<?> toReturn;
        try {
            historialClinicoService.create(historialClinicoOutDto);
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody HistorialClinicoOutDto historialClinicoOutDto) {
        ResponseEntity<?> toReturn;
        try {
            HistorialClinico historialClinico = dtoToEntityConverter.convert(historialClinicoOutDto);
            historialClinicoService.update(historialClinico);
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
            historialClinicoService.deleteHistorialClinico(id);
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

