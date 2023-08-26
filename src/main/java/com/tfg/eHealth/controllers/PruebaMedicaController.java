package com.tfg.eHealth.controllers;

import com.tfg.eHealth.converter.DtoToEntityConverter;
import com.tfg.eHealth.converter.EntityToDtoConverter;
import com.tfg.eHealth.dtos.PruebaMedicaInDto;
import com.tfg.eHealth.dtos.PruebaMedicaOutDto;
import com.tfg.eHealth.entities.PruebaMedica;
import com.tfg.eHealth.services.PruebaMedicaService;
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
@RequestMapping("/prueba-medica")
public class PruebaMedicaController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PruebaMedicaService pruebaMedicaService;

    @Autowired
    private EntityToDtoConverter entityToDtoConverter;

    @Autowired
    private DtoToEntityConverter dtoToEntityConverter;

    @GetMapping("/by-medico/{id}")
    public ResponseEntity<?> getPruebasMedicasListByMedicoWithoutResults(@PathVariable Long id) {
        ResponseEntity<?> toReturn;
        List<PruebaMedica> pruebasMedicas = pruebaMedicaService.getWithoutResultPruebasMedicasByMedico(id);
        if (pruebasMedicas.isEmpty()) {
            toReturn = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<PruebaMedicaOutDto> appRes = pruebasMedicas.stream()
                    .map(entityToDtoConverter::convert)
                    .collect(Collectors.toList());
            toReturn = new ResponseEntity<>(appRes, HttpStatus.OK);
        }
        return toReturn;
    }

    @GetMapping("/by-paciente/{id}")
    public ResponseEntity<?> getPruebasMedicasListByPaciente(@PathVariable Long id) {
        ResponseEntity<?> toReturn;
        List<PruebaMedica> pruebasMedicas = pruebaMedicaService.getPruebasMedicasByPaciente(id);
        if (pruebasMedicas.isEmpty()) {
            toReturn = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<PruebaMedicaOutDto> appRes = pruebasMedicas.stream()
                    .map(entityToDtoConverter::convert)
                    .collect(Collectors.toList());
            toReturn = new ResponseEntity<>(appRes, HttpStatus.OK);
        }
        return toReturn;
    }

    @GetMapping("/by-solicitud/{id}")
    public ResponseEntity<?> getPruebasMedicasListBySolicitud(@PathVariable Long id) {
        ResponseEntity<?> toReturn;
        List<PruebaMedica> pruebasMedicas = pruebaMedicaService.getPruebasMedicasBySolicitud(id);
        if (pruebasMedicas.isEmpty()) {
            toReturn = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<PruebaMedicaOutDto> appRes = pruebasMedicas.stream()
                    .map(entityToDtoConverter::convert)
                    .collect(Collectors.toList());
            toReturn = new ResponseEntity<>(appRes, HttpStatus.OK);
        }
        return toReturn;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPruebaMedicaById(@PathVariable Long id) {
        ResponseEntity<?> toReturn;
        try {
            PruebaMedica pruebaMedica = pruebaMedicaService.getPruebaMedicaById(id);
            PruebaMedicaOutDto appRes = entityToDtoConverter.convert(pruebaMedica);
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

    @PutMapping("/add-results/{id}")
    public ResponseEntity<?> addPruebaMedicaResults(@PathVariable Long id, @RequestBody String resultsUrl) {
        ResponseEntity<?> toReturn;
        try {
            pruebaMedicaService.addResultsUrl(resultsUrl, id);
            toReturn = new ResponseEntity<>(HttpStatus.OK);
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
    public ResponseEntity<?> create(@RequestBody PruebaMedicaInDto pruebaMedicaDto) {
        ResponseEntity<?> toReturn;
        try {
            PruebaMedica pruebaMedica = dtoToEntityConverter.convert(pruebaMedicaDto);
            pruebaMedicaService.create(pruebaMedica, pruebaMedicaDto.getSolicitudId());
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PruebaMedicaInDto pruebaMedicaDto) {
        ResponseEntity<?> toReturn;
        try {
            PruebaMedica pruebaMedica = dtoToEntityConverter.convert(pruebaMedicaDto);
            pruebaMedicaService.update(pruebaMedica, id);
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
            pruebaMedicaService.deletePruebaMedica(id);
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

