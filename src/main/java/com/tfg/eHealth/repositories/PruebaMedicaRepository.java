package com.tfg.eHealth.repositories;

import com.tfg.eHealth.entities.PruebaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface PruebaMedicaRepository extends JpaRepository<PruebaMedica, Long>, JpaSpecificationExecutor<PruebaMedica> {

}
