package com.tfg.eHealth.repositories;

import com.tfg.eHealth.entities.SolicitudConsulta;
import com.tfg.eHealth.vo.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface SolicitudConsultaRepository extends JpaRepository<SolicitudConsulta, Long>, JpaSpecificationExecutor<SolicitudConsulta> {
    List<SolicitudConsulta> findAllByPaciente_Id(long id);
    List<SolicitudConsulta> findAllByMedico_Id(long id);
}
