package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReporteEstudianteProcessorTest {

    @Test
    void shouldMarkApprovedWhenAverageIsSeventy() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Ana");
        estudiante.setGrupo("A");
        estudiante.setPromedio(70.0);

        ReporteEstudianteProcessor processor = new ReporteEstudianteProcessor();
        EstudianteReporte reporte = processor.process(estudiante);

        assertEquals("APROBADO", reporte.getEstado());
    }

    @Test
    void shouldMarkFailedWhenAverageIsBelowSeventy() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Ana");
        estudiante.setGrupo("A");
        estudiante.setPromedio(69.9);

        ReporteEstudianteProcessor processor = new ReporteEstudianteProcessor();
        EstudianteReporte reporte = processor.process(estudiante);

        assertEquals("REPROBADO", reporte.getEstado());
    }
}
