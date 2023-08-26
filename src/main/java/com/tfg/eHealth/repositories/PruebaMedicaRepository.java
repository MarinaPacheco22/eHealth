package com.tfg.eHealth.repositories;

import com.tfg.eHealth.entities.PruebaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PruebaMedicaRepository extends JpaRepository<PruebaMedica, Long>, JpaSpecificationExecutor<PruebaMedica> {
    List<PruebaMedica> findAllByResultadosUrlIsNullAndSolicitudConsulta_Medico_Id(Long id);
    List<PruebaMedica> findAllBySolicitudConsulta_Paciente_Id(Long id);
    List<PruebaMedica> findAllBySolicitudConsulta_Id(Long id);
}
