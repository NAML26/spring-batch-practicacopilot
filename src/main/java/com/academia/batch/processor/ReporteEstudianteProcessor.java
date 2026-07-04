package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ReporteEstudianteProcessor implements ItemProcessor<Estudiante, EstudianteReporte> {

    private static final Logger logger = LoggerFactory.getLogger(ReporteEstudianteProcessor.class);

    @Override
    public EstudianteReporte process(Estudiante estudiante) {
        EstudianteReporte reporte = new EstudianteReporte();
        reporte.setNombre(estudiante.getNombre());
        reporte.setGrupo(estudiante.getGrupo());
        reporte.setPromedio(estudiante.getPromedio());
        reporte.setEstado(estudiante.getPromedio() >= 70 ? "APROBADO" : "REPROBADO");
        logger.info("Step 2 - Reporte: {}", reporte);
        return reporte;
    }
}
