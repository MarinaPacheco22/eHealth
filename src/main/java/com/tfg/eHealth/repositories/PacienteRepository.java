package com.tfg.eHealth.repositories;

import com.tfg.eHealth.entities.Medico;
import com.tfg.eHealth.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>, JpaSpecificationExecutor<Paciente> {
    List<Paciente> findByMedicoAsignado(Medico medicoAsignado);
    Optional<Paciente> findByEmail(String email);
    Paciente getById(long id);

}
