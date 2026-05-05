package com.innovatech.controller;

import com.innovatech.model.Tarea;
import com.innovatech.service.TareaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;

    @PostMapping("/proyecto/{proyectoId}")
    public ResponseEntity<Tarea> crearTarea(
            @PathVariable Long proyectoId,
            @Valid @RequestBody Tarea tarea
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tareaService.crearTarea(proyectoId, tarea));
    }

    @GetMapping
    public ResponseEntity<List<Tarea>> listarTareas() {
        return ResponseEntity.ok(tareaService.listarTareas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerTareaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tareaService.obtenerTareaPorId(id));
    }

    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<Tarea>> listarTareasPorProyecto(@PathVariable Long proyectoId) {
        return ResponseEntity.ok(tareaService.listarTareasPorProyecto(proyectoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(
            @PathVariable Long id,
            @Valid @RequestBody Tarea tarea
    ) {
        return ResponseEntity.ok(tareaService.actualizarTarea(id, tarea));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Tarea>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(tareaService.buscarPorEstado(estado));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Tarea>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(tareaService.buscarPorTitulo(titulo));
    }
}