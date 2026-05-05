package com.innovatech.controller;

import com.innovatech.model.Recurso;
import com.innovatech.service.RecursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/recursos")
@RequiredArgsConstructor
public class RecursoController {

    private final RecursoService recursoService;

    @PostMapping
    public ResponseEntity<Recurso> crearRecurso(@Valid @RequestBody Recurso recurso) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recursoService.crearRecurso(recurso));
    }

    @GetMapping
    public ResponseEntity<List<Recurso>> listarRecursos() {
        return ResponseEntity.ok(recursoService.listarRecursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recurso> obtenerRecursoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(recursoService.obtenerRecursoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recurso> actualizarRecurso(
            @PathVariable Long id,
            @Valid @RequestBody Recurso recurso
    ) {
        return ResponseEntity.ok(recursoService.actualizarRecurso(id, recurso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecurso(@PathVariable Long id) {
        recursoService.eliminarRecurso(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Recurso>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(recursoService.buscarPorEstado(estado));
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Recurso>> buscarPorRol(@PathVariable String rol) {
        return ResponseEntity.ok(recursoService.buscarPorRol(rol));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Recurso>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(recursoService.buscarPorNombre(nombre));
    }
}
