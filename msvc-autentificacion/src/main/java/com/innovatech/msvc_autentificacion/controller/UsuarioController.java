package com.innovatech.msvc_autentificacion.controller;

import com.innovatech.msvc_autentificacion.dto.UsuarioDTO;
import com.innovatech.msvc_autentificacion.model.EstadoUsuario;
import com.innovatech.msvc_autentificacion.model.RolUsuario;
import com.innovatech.msvc_autentificacion.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Logger;

public class UsuarioController {
    private static final Logger logger = Logger.getLogger(UsuarioController.class.getName());

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST /api/v1/auth/registro — Registrar nuevo usuario
    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO.Response> registrar(
            @Valid @RequestBody UsuarioDTO.Request request) {
        logger.info("POST /api/v1/auth/registro - " + request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrar(request));
    }

    // POST /api/v1/auth/login — Iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO.LoginResponse> login(
            @Valid @RequestBody UsuarioDTO.LoginRequest request) {
        logger.info("POST /api/v1/auth/login - " + request.getEmail());
        return ResponseEntity.ok(usuarioService.login(request));
    }

    // GET /api/v1/auth/usuarios — Listar todos los usuarios
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO.Response>> obtenerTodos(
            @RequestParam(required = false) RolUsuario rol,
            @RequestParam(required = false) EstadoUsuario estado) {

        if (rol != null) return ResponseEntity.ok(usuarioService.obtenerPorRol(rol));
        if (estado != null) return ResponseEntity.ok(usuarioService.obtenerPorEstado(estado));
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    // GET /api/v1/auth/usuarios/{id} — Obtener usuario por ID
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO.Response> obtenerPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/auth/usuarios/" + id);
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    // PUT /api/v1/auth/usuarios/{id} — Actualizar usuario
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO.Request request) {
        logger.info("PUT /api/v1/auth/usuarios/" + id);
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    // PATCH /api/v1/auth/usuarios/{id}/estado — Cambiar estado
    @PatchMapping("/usuarios/{id}/estado")
    public ResponseEntity<UsuarioDTO.Response> cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoUsuario estado) {
        logger.info("PATCH /api/v1/auth/usuarios/" + id + "/estado -> " + estado);
        return ResponseEntity.ok(usuarioService.cambiarEstado(id, estado));
    }

    // DELETE /api/v1/auth/usuarios/{id} — Eliminar usuario
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/auth/usuarios/" + id);
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
