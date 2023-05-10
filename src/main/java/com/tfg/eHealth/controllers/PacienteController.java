package com.tfg.eHealth.controllers;

import com.tfg.eHealth.converter.DtoToEntityConverter;
import com.tfg.eHealth.converter.EntityToDtoConverter;
import com.tfg.eHealth.dtos.PacienteDto;
import com.tfg.eHealth.entities.Paciente;
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
@RequestMapping("/paciente")
public class PacienteController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private EntityToDtoConverter entityToDtoConverter;

    @Autowired
    private DtoToEntityConverter dtoToEntityConverter;

    @GetMapping
    public ResponseEntity<?> getPacienteList() {
        ResponseEntity<?> toReturn;
        List<Paciente> pacientes = pacienteService.getAllPacientes();
        if (pacientes.isEmpty()) {
            toReturn = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<PacienteDto> appRes = pacientes.stream()
                    .map(entityToDtoConverter::convert)
                    .collect(Collectors.toList());
            toReturn = new ResponseEntity<>(appRes, HttpStatus.OK);
        }
        return toReturn;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPacienteById(@PathVariable Long id) {
        ResponseEntity<?> toReturn;
        try {
            Paciente paciente = pacienteService.getPacienteById(id);
            PacienteDto appRes = entityToDtoConverter.convert(paciente);
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

    @GetMapping("/by-medico/{id}")
    public ResponseEntity<?> getPacientesByMedicoId(@PathVariable Long id) {
        ResponseEntity<?> toReturn;
        try {
            List<Paciente> pacientes = pacienteService.getPacienteByMedicoId(id);
            List<PacienteDto> appRes = pacientes.stream()
                    .map(entityToDtoConverter::convert)
                    .collect(Collectors.toList());
            if (pacientes.isEmpty()) {
                toReturn = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                toReturn = new ResponseEntity<>(appRes, HttpStatus.OK);
            }
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
    public ResponseEntity<?> create(@RequestBody PacienteDto pacienteDto) {
        ResponseEntity<?> toReturn;
        try {
            Paciente paciente = dtoToEntityConverter.convert(pacienteDto);
            pacienteService.create(paciente);
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PacienteDto pacienteDto) {
        ResponseEntity<?> toReturn;
        try {
            Paciente paciente = dtoToEntityConverter.convert(pacienteDto);
            pacienteService.update(paciente, id);
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
            pacienteService.deletePaciente(id);
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

