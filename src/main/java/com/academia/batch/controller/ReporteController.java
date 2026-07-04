package com.academia.batch.controller;

import com.academia.batch.model.EstudianteReporte;
import com.academia.batch.repository.ReporteRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteRepository reporteRepository;

    public ReporteController(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    @GetMapping
    public List<EstudianteReporte> listarTodos() {
        return reporteRepository.findAll();
    }

    @GetMapping("/estado/{estado}")
    public List<EstudianteReporte> porEstado(@PathVariable String estado) {
        return reporteRepository.findByEstado(estado.toUpperCase());
    }
}
