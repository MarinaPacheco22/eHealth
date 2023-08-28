package com.tfg.eHealth.services;

import com.tfg.eHealth.converter.DtoToEntityConverter;
import com.tfg.eHealth.converter.EntityToDtoConverter;
import com.tfg.eHealth.dtos.PacienteOutDto;
import com.tfg.eHealth.entities.HistorialClinico;
import com.tfg.eHealth.entities.Medico;
import com.tfg.eHealth.entities.Paciente;
import com.tfg.eHealth.repositories.HistorialClinicoRepository;
import com.tfg.eHealth.repositories.MedicoRepository;
import com.tfg.eHealth.repositories.PacienteRepository;
import com.tfg.eHealth.utils.EntitySpecificationsBuilder;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PacienteService {

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    HistorialClinicoRepository historialClinicoRepository;

    @Autowired
    DtoToEntityConverter dtoToEntityConverter;

    @Autowired
    EntityToDtoConverter entityToDtoConverter;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente getPacienteById(Long id) throws NotFoundException {
        Optional<Paciente> byId = pacienteRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Paciente con id <" + id + "> no encontrado.");
        }

        return byId.get();
    }

    public Paciente getPacienteByEmail(String email) throws NotFoundException {
        Optional<Paciente> byEmail = pacienteRepository.findByEmail(email);

        if (byEmail.isEmpty()) {
            throw new NotFoundException("Usuario con email <" + email + "> no encontrado.");
        }

        return byEmail.get();
    }

    public Long create(PacienteOutDto outDto) {
        String email = outDto.getEmail();
        Optional<Medico> medicoByEmail= medicoRepository.findByEmail(email);
        Optional<Paciente> pacienteByEmail = pacienteRepository.findByEmail(email);
        if (medicoByEmail.isPresent() || pacienteByEmail.isPresent()) {
            throw new DuplicateKeyException("Este email ya está registrado.");
        }
        Paciente paciente = dtoToEntityConverter.convert(outDto);
        List<Medico> medicoAsignado = medicoRepository.getMedicoFamiliarWithLessAsignations();
        paciente.setMedicoAsignado(medicoAsignado.get(0));
        Paciente pacienteCreated = pacienteRepository.save(paciente);
        HistorialClinico historialClinico = dtoToEntityConverter.convertToHistorialClinico(outDto);
        historialClinico.setPaciente(pacienteCreated);
        HistorialClinico historialClinicoCreated = historialClinicoRepository.save(historialClinico);
        pacienteCreated.setHistorialClinico(historialClinicoCreated);
        Paciente created = pacienteRepository.save(pacienteCreated);
        return created.getId();
    }

    @Transactional
    public void update(Paciente toUpdate) throws NotFoundException {
        Paciente byId = pacienteRepository.getById(toUpdate.getId());

        byId.setNombre(toUpdate.getNombre());
        byId.setApellidos(toUpdate.getApellidos());
        byId.setDni(toUpdate.getDni());
        byId.setNumSegSocial(toUpdate.getNumSegSocial());
        byId.setTelefono(toUpdate.getTelefono());
        byId.setEmail(toUpdate.getEmail());
        byId.setAltura(toUpdate.getAltura());
        byId.setPeso(toUpdate.getPeso());
        byId.setFechaNacimiento(toUpdate.getFechaNacimiento());
        byId.setFumador(toUpdate.getFumador());
        entityManager.merge(byId);
    }

    public void deletePaciente(Long id) throws NotFoundException {
        Optional<Paciente> byId = pacienteRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException("Paciente con id <" + id + "> no existe.");
        }

        pacienteRepository.delete(byId.get());
    }

    public List<Paciente> getPacienteByMedicoId(Long id) throws NotFoundException {
        Optional<Medico> medico = medicoRepository.findById(id);

        if (medico.isEmpty()) {
            throw new NotFoundException("Medico con id <" + id + "> no existe.");
        }

        return pacienteRepository.findByMedicoAsignado(medico.get());
    }

    @Transactional
    public PacienteOutDto changePassword(Long id, String newPassword) throws NotFoundException {
        Optional<Paciente> paciente = pacienteRepository.findById(id);

        if (paciente.isEmpty()) {
            throw new NotFoundException("Paciente con id <" + id + "> no encontrado.");
        }

        paciente.get().setPassword(newPassword);
        entityManager.merge(paciente.get());

        return entityToDtoConverter.convertOut(paciente.get());
    }


    public List<Paciente> getPacientesFiltrados(String search) {
        Specification<Paciente> spec = getMedicoSpecification(search);
        return pacienteRepository.findAll(spec);
    }

    private Specification<Paciente> getMedicoSpecification(String search) {
        search = Normalizer
                .normalize(search, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        EntitySpecificationsBuilder<Paciente> builder = new EntitySpecificationsBuilder<>();
        Pattern pattern = Pattern.compile("(\\w+(?:\\.\\w+)*)(/|:|<|>)((?>\\w|-| |:|/)+?),");
        Matcher matcher = pattern.matcher(search + ",");

        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        return builder.build();
    }
}
