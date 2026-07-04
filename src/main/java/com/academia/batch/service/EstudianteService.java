package com.academia.batch.service;

import com.academia.batch.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio para operaciones de negocio relacionadas con estudiantes.
 */
@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    /**
     * Crea una nueva instancia del servicio con el repositorio requerido.
     *
     * @param estudianteRepository repositorio de estudiantes
     */
    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    /**
     * Cuenta cuántos estudiantes tienen promedio mayor o igual a 70.
     *
     * @return cantidad de estudiantes aprobados
     */
    public long contarAprobados() {
        return estudianteRepository.findAll().stream()
                .filter(estudiante -> estudiante.getPromedio() >= 70)
                .count();
    }
}
