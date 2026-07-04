package com.academia.batch.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {

    List<EstudianteEntity> findByGrupo(String grupo);
}
