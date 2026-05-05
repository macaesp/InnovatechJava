package com.innovatech.controller;

import com.innovatech.model.Proyecto;
import com.innovatech.service.ProyectoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @PostMapping
    public ResponseEntity<Proyecto> crearProyecto(@Valid @RequestBody Proyecto proyecto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(proyectoService.crearProyecto(proyecto));
    }

    @GetMapping
    public ResponseEntity<List<Proyecto>> listarProyectos() {
        return ResponseEntity.ok(proyectoService.listarProyectos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.obtenerProyectoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyecto(
            @PathVariable Long id,
            @Valid @RequestBody Proyecto proyecto
    ) {
        return ResponseEntity.ok(proyectoService.actualizarProyecto(id, proyecto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Proyecto>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(proyectoService.buscarPorEstado(estado));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Proyecto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(proyectoService.buscarPorNombre(nombre));
    }
}