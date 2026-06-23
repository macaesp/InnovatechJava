package com.innovatech.controller;

import com.innovatech.model.Colaboracion;
import com.innovatech.service.ColaboracionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colaboraciones")
public class ColaboracionController {

    private final ColaboracionService colaboracionService;

    public ColaboracionController(ColaboracionService colaboracionService) {
        this.colaboracionService = colaboracionService;
    }

    @PostMapping
    public ResponseEntity<Colaboracion> crearColaboracion(@Valid @RequestBody Colaboracion colaboracion) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(colaboracionService.crearColaboracion(colaboracion));
    }

    @GetMapping
    public ResponseEntity<List<Colaboracion>> listarColaboraciones() {
        return ResponseEntity.ok(colaboracionService.listarColaboraciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colaboracion> obtenerColaboracionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(colaboracionService.obtenerColaboracionPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colaboracion> actualizarColaboracion(
            @PathVariable Long id,
            @Valid @RequestBody Colaboracion colaboracion
    ) {
        return ResponseEntity.ok(colaboracionService.actualizarColaboracion(id, colaboracion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarColaboracion(@PathVariable Long id) {
        colaboracionService.eliminarColaboracion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Colaboracion>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(colaboracionService.buscarPorEstado(estado));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Colaboracion>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(colaboracionService.buscarPorNombre(nombre));
    }
}