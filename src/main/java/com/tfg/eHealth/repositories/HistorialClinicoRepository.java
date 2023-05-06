package com.tfg.eHealth.repositories;

import com.tfg.eHealth.entities.HistorialClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface HistorialClinicoRepository extends JpaRepository<HistorialClinico, Long>, JpaSpecificationExecutor<HistorialClinico> {

}
