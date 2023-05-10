package com.tfg.eHealth.controllers;

import com.tfg.eHealth.converter.DtoToEntityConverter;
import com.tfg.eHealth.converter.EntityToDtoConverter;
import com.tfg.eHealth.dtos.HistorialClinicoDto;
import com.tfg.eHealth.entities.HistorialClinico;
import com.tfg.eHealth.services.HistorialClinicoService;
import javassist.NotFoundException;
import org.aspectj.weaver.ast.Not;
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
            List<HistorialClinicoDto> appRes = historialesClinicos.stream()
                    .map(entityToDtoConverter::convert)
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
            HistorialClinicoDto appRes = entityToDtoConverter.convert(historialClinico);
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
    public ResponseEntity<?> create(@RequestBody HistorialClinicoDto historialClinicoDto) {
        ResponseEntity<?> toReturn;
        try {
            HistorialClinico historialClinico = dtoToEntityConverter.convert(historialClinicoDto);
            historialClinicoService.create(historialClinico);
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody HistorialClinicoDto historialClinicoDto) {
        ResponseEntity<?> toReturn;
        try {
            HistorialClinico historialClinico = dtoToEntityConverter.convert(historialClinicoDto);
            historialClinicoService.update(historialClinico, id);
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

