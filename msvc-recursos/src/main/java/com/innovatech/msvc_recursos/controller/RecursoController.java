package com.innovatech.msvc_recursos.controller;

import com.innovatech.msvc_recursos.dto.RecursoDTO;
import com.innovatech.msvc_recursos.model.EstadoRecurso;
import com.innovatech.msvc_recursos.model.TipoRecurso;
import com.innovatech.msvc_recursos.service.RecursoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/recursos")
public class RecursoController {

    private static final Logger logger = Logger.getLogger(RecursoController.class.getName());

    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    // POST /api/v1/recursos
    @PostMapping
    public ResponseEntity<RecursoDTO.Response> crear(@Valid @RequestBody RecursoDTO.Request request) {
        logger.info("POST /api/v1/recursos - " + request.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(recursoService.crear(request));
    }

    // GET /api/v1/recursos
    @GetMapping
    public ResponseEntity<List<RecursoDTO.Response>> obtenerTodos(
            @RequestParam(required = false) EstadoRecurso estado,
            @RequestParam(required = false) TipoRecurso tipo,
            @RequestParam(required = false) Long proyectoId,
            @RequestParam(required = false) String nombre) {

        if (estado != null) return ResponseEntity.ok(recursoService.obtenerPorEstado(estado));
        if (tipo != null) return ResponseEntity.ok(recursoService.obtenerPorTipo(tipo));
        if (proyectoId != null) return ResponseEntity.ok(recursoService.obtenerPorProyecto(proyectoId));
        if (nombre != null && !nombre.isBlank()) return ResponseEntity.ok(recursoService.buscarPorNombre(nombre));
        return ResponseEntity.ok(recursoService.obtenerTodos());
    }

    // GET /api/v1/recursos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RecursoDTO.Response> obtenerPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/recursos/" + id);
        return ResponseEntity.ok(recursoService.obtenerPorId(id));
    }

    // PUT /api/v1/recursos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<RecursoDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RecursoDTO.Request request) {
        logger.info("PUT /api/v1/recursos/" + id);
        return ResponseEntity.ok(recursoService.actualizar(id, request));
    }

    // PATCH /api/v1/recursos/{id}/estado
    @PatchMapping("/{id}/estado")
    public ResponseEntity<RecursoDTO.Response> cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoRecurso estado) {
        logger.info("PATCH /api/v1/recursos/" + id + "/estado -> " + estado);
        return ResponseEntity.ok(recursoService.cambiarEstado(id, estado));
    }

    // DELETE /api/v1/recursos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/recursos/" + id);
        recursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}