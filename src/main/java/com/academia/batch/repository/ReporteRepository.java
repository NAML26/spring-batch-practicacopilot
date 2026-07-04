package com.academia.batch.repository;

import com.academia.batch.model.EstudianteReporte;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReporteRepository extends MongoRepository<EstudianteReporte, String> {

    List<EstudianteReporte> findByEstado(String estado);
}
