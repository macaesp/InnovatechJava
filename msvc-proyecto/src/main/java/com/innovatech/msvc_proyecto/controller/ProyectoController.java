package com.innovatech.msvc_proyecto.controller;

import com.innovatech.msvc_proyecto.dto.ProyectoDTO;
import com.innovatech.msvc_proyecto.model.EstadoProyecto;
import com.innovatech.msvc_proyecto.service.ProyectoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/proyectos")
@RequiredArgsConstructor
@Slf4j
public class ProyectoController {

    private final ProyectoService proyectoService;

    // POST /api/v1/proyectos — ver solamente un proyecto
    @PostMapping
    public ResponseEntity<ProyectoDTO.Response> crear(
            @Valid @RequestBody ProyectoDTO.Request request) {
        log.info("POST /api/v1/proyectos - {}", request.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(proyectoService.crear(request));
    }

    // GET /api/v1/proyectos — Listar todos los proyectos
    @GetMapping
    public ResponseEntity<List<ProyectoDTO.Response>> obtenerTodos(
            @RequestParam(required = false) EstadoProyecto estado,
            @RequestParam(required = false) String nombre) {

        if (estado != null) return ResponseEntity.ok(proyectoService.obtenerPorEstado(estado));
        if (nombre != null && !nombre.isBlank()) return ResponseEntity.ok(proyectoService.buscarPorNombre(nombre));
        return ResponseEntity.ok(proyectoService.obtenerTodos());
    }

    // GET /api/v1/proyectos/{id} — Obtener por proyecto por id
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDTO.Response> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/proyectos/{}", id);
        return ResponseEntity.ok(proyectoService.obtenerPorId(id));
    }

    // PUT /api/v1/proyectos/{id} — Actualizar el proyecto
    @PutMapping("/{id}")
    public ResponseEntity<ProyectoDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProyectoDTO.Request request) {
        log.info("PUT /api/v1/proyectos/{}", id);
        return ResponseEntity.ok(proyectoService.actualizar(id, request));
    }

    // PATCH /api/v1/proyectos/{id}/estado — Cambiar el estado del proyecto
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ProyectoDTO.Response> cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoProyecto estado) {
        log.info("PATCH /api/v1/proyectos/{}/estado → {}", id, estado);
        return ResponseEntity.ok(proyectoService.cambiarEstado(id, estado));
    }

    // DELETE /api/v1/proyectos/{id} — Eliminar proyecto por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/proyectos/{}", id);
        proyectoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}