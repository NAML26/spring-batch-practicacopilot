package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EstudianteProcessorTest {

    @Test
    void shouldCalculateAverageCorrectly() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNota1(80);
        estudiante.setNota2(70);
        estudiante.setNota3(60);

        EstudianteProcessor processor = new EstudianteProcessor();
        Estudiante resultado = processor.process(estudiante);

        assertEquals(70.0, resultado.getPromedio());
    }
}
