package com.tfg.eHealth.repositories;

import com.tfg.eHealth.entities.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>, JpaSpecificationExecutor<Medico> {

    @Query("SELECT m FROM Medico m WHERE m.especialidad = 'Medicina familiar' ORDER BY SIZE(m.pacientesAsignados) ASC")
    Medico getMedicoFamiliarWithLessAsignations();

    Optional<Medico> findByEmail(String email);

}
