package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class EstudianteProcessor implements ItemProcessor<Estudiante, Estudiante> {

    private static final Logger logger = LoggerFactory.getLogger(EstudianteProcessor.class);

    @Override
    public Estudiante process(Estudiante estudiante) {
        double promedio = (estudiante.getNota1() + estudiante.getNota2() + estudiante.getNota3()) / 3;
        estudiante.setPromedio(promedio);
        logger.info("Step 1 - Procesando: {}", estudiante);
        return estudiante;
    }
}
