package com.academia.batch.controller;

import com.academia.batch.repository.EstudianteEntity;
import com.academia.batch.repository.EstudianteRepository;
import com.academia.batch.service.EstudianteService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteRepository estudianteRepository;
    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteRepository estudianteRepository, EstudianteService estudianteService) {
        this.estudianteRepository = estudianteRepository;
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public List<EstudianteEntity> listarTodos() {
        return estudianteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteEntity> obtenerPorId(@PathVariable Long id) {
        return estudianteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/aprobados/total")
    public Map<String, Long> totalAprobados() {
        Map<String, Long> respuesta = new HashMap<>();
        respuesta.put("total", estudianteService.contarAprobados());
        return respuesta;
    }

    @PostMapping
    public ResponseEntity<EstudianteEntity> crear(@RequestBody EstudianteEntity estudiante) {
        EstudianteEntity guardado = estudianteRepository.save(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteEntity> reemplazar(@PathVariable Long id,
                                                       @RequestBody EstudianteEntity estudiante) {
        return estudianteRepository.findById(id)
                .map(existente -> {
                    estudiante.setId(id);
                    EstudianteEntity actualizado = estudianteRepository.save(estudiante);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EstudianteEntity> actualizarGrupo(@PathVariable Long id,
                                                            @RequestBody Map<String, Object> cambios) {
        return estudianteRepository.findById(id)
                .map(estudiante -> {
                    Object grupo = cambios.get("grupo");
                    if (grupo != null) {
                        estudiante.setGrupo(grupo.toString());
                    }
                    return ResponseEntity.ok(estudianteRepository.save(estudiante));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return estudianteRepository.findById(id)
                .map(estudiante -> {
                    estudianteRepository.delete(estudiante);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
