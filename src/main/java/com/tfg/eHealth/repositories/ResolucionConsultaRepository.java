package com.tfg.eHealth.repositories;

import com.tfg.eHealth.entities.PruebaMedica;
import com.tfg.eHealth.entities.ResolucionConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ResolucionConsultaRepository extends JpaRepository<ResolucionConsulta, Long>, JpaSpecificationExecutor<ResolucionConsulta> {

}
