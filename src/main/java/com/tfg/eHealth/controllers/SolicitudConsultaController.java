package com.tfg.eHealth.controllers;

import com.tfg.eHealth.converter.DtoToEntityConverter;
import com.tfg.eHealth.converter.EntityToDtoConverter;
import com.tfg.eHealth.dtos.ArchivoOutDto;
import com.tfg.eHealth.dtos.SolicitudConsultaInDto;
import com.tfg.eHealth.dtos.SolicitudConsultaOutDto;
import com.tfg.eHealth.entities.SolicitudConsulta;
import com.tfg.eHealth.services.SolicitudConsultaService;
import com.tfg.eHealth.vo.Archivo;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/solicitud-consulta")
public class SolicitudConsultaController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SolicitudConsultaService solicitudConsultaService;

    @Autowired
    private EntityToDtoConverter entityToDtoConverter;

    @Autowired
    private DtoToEntityConverter dtoToEntityConverter;

    @GetMapping
    public ResponseEntity<?> getSolicitudConsultaList() {
        ResponseEntity<?> toReturn;
        List<SolicitudConsulta> solicitudesConsulta = solicitudConsultaService.getAllSolicitudesConsulta();
        if (solicitudesConsulta.isEmpty()) {
            toReturn = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<SolicitudConsultaInDto> appRes = solicitudesConsulta.stream()
                    .map(entityToDtoConverter::convertIn)
                    .collect(Collectors.toList());
            toReturn = new ResponseEntity<>(appRes, HttpStatus.OK);
        }
        return toReturn;
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getSolicitudesConsultaFilteredList(@RequestParam(required = false, value = "filter") String search) {
        ResponseEntity<?> toReturn;
        List<SolicitudConsulta> solicitudesConsulta = solicitudConsultaService.getSolicitudesConsultaFiltradas(search);
        if (solicitudesConsulta.isEmpty()) {
            toReturn = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<SolicitudConsultaOutDto> appRes = solicitudesConsulta.stream()
                    .map(entityToDtoConverter::convertOut)
                    .collect(Collectors.toList());
            toReturn = new ResponseEntity<>(appRes, HttpStatus.OK);
        }
        return toReturn;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSolicitudConsultaById(@PathVariable Long id) {
        ResponseEntity<?> toReturn;
        try {
            SolicitudConsulta solicitudConsulta = solicitudConsultaService.getSolicitudConsultaById(id);
            SolicitudConsultaInDto appRes = entityToDtoConverter.convertIn(solicitudConsulta);
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
    public ResponseEntity<?> getSolicitudesConsultaByPacienteId(@PathVariable Long id) {
        ResponseEntity<?> toReturn;
        List<SolicitudConsulta> solicitudesConsulta = solicitudConsultaService.getSolicitudesConsultaByPacienteId(id);
        if (solicitudesConsulta.isEmpty()) {
            toReturn = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<SolicitudConsultaOutDto> appRes = solicitudesConsulta.stream()
                    .map(entityToDtoConverter::convertOut)
                    .collect(Collectors.toList());
            toReturn = new ResponseEntity<>(appRes, HttpStatus.OK);
        }
        return toReturn;
    }

    @GetMapping("/by-medico/{id}")
    public ResponseEntity<?> getSolicitudesConsultaByMedicoId(@PathVariable Long id) {
        ResponseEntity<?> toReturn;
        List<SolicitudConsulta> solicitudesConsulta = solicitudConsultaService.getSolicitudesConsultaByMedicoId(id);
        if (solicitudesConsulta.isEmpty()) {
            toReturn = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<SolicitudConsultaOutDto> appRes = solicitudesConsulta.stream()
                    .map(entityToDtoConverter::convertOut)
                    .collect(Collectors.toList());
            toReturn = new ResponseEntity<>(appRes, HttpStatus.OK);
        }
        return toReturn;
    }



    @GetMapping("/archivos/{solicitudId}")
    public ResponseEntity<?> getArchivosBySolicitudesConsultaId(@PathVariable Long solicitudId) throws NotFoundException {
        ResponseEntity<?> toReturn;
        List<Archivo> archivos = solicitudConsultaService.getArchivosBySolicitudesConsultaId(solicitudId);
        if (archivos.isEmpty()) {
            toReturn = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<ArchivoOutDto> appRes = entityToDtoConverter.convert(archivos);
            toReturn = new ResponseEntity<>(appRes, HttpStatus.OK);
        }
        return toReturn;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@RequestPart("archivos") MultipartFile[] archivos,
                                    @RequestPart("descripcion") String descripcion,
                                    @RequestPart("pacienteId") String pacienteIdStr,
                                    @RequestPart("medicoId") String medicoIdStr) {
        ResponseEntity<?> toReturn;
        try {
            solicitudConsultaService.create(archivos, descripcion, pacienteIdStr, medicoIdStr);
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
    public ResponseEntity<?> update(@PathVariable Long id, SolicitudConsultaInDto solicitudConsultaDto) {
        ResponseEntity<?> toReturn;
        try {
            SolicitudConsulta solicitudConsulta = dtoToEntityConverter.convert(solicitudConsultaDto);
            solicitudConsultaService.update(solicitudConsulta, id);
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
            solicitudConsultaService.deleteSolicitudConsulta(id);
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

    @PutMapping("/update-state/{id}/{state}")
    public ResponseEntity<?> update(@PathVariable Long id, @PathVariable Long state) {
        ResponseEntity<?> toReturn;
        try {
            solicitudConsultaService.updateState(id, state);
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

}

