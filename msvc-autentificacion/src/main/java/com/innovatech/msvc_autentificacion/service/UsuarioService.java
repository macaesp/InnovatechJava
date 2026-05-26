package com.innovatech.msvc_autentificacion.service;

import com.innovatech.msvc_autentificacion.dto.UsuarioDTO;
import com.innovatech.msvc_autentificacion.exception.UsuarioNoEncontradoException;
import com.innovatech.msvc_autentificacion.model.EstadoUsuario;
import com.innovatech.msvc_autentificacion.model.RolUsuario;
import com.innovatech.msvc_autentificacion.model.Usuario;
import com.innovatech.msvc_autentificacion.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {
    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //
    public UsuarioDTO.Response registrar(UsuarioDTO.Request request) {
        logger.info("Registrando nuevo usuario: " + request.getEmail());

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + request.getEmail());
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());
        usuario.setRol(request.getRol() != null ? request.getRol() : RolUsuario.USER);
        usuario.setEstado(EstadoUsuario.ACTIVO);

        Usuario guardado = usuarioRepository.save(usuario);
        logger.info("Usuario registrado con id: " + guardado.getId());
        return mapearAResponse(guardado);
    }

    //
    @Transactional(readOnly = true)
    public UsuarioDTO.LoginResponse login(UsuarioDTO.LoginRequest request) {
        logger.info("Intento de login: " + request.getEmail());

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));

        if (!usuario.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        if (usuario.getEstado() == EstadoUsuario.INACTIVO || usuario.getEstado() == EstadoUsuario.BLOQUEADO) {
            throw new IllegalArgumentException("Usuario inactivo o bloqueado");
        }

        logger.info("Login exitoso para: " + request.getEmail());

        UsuarioDTO.LoginResponse response = new UsuarioDTO.LoginResponse();
        response.setMensaje("Login exitoso");
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setEmail(usuario.getEmail());
        response.setRol(usuario.getRol());
        return response;
    }

    //
    @Transactional(readOnly = true)
    public List<UsuarioDTO.Response> obtenerTodos() {
        logger.info("Obteniendo todos los usuarios");
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO.Response obtenerPorId(Long id) {
        logger.info("Buscando usuario con id: " + id);
        return mapearAResponse(buscarPorId(id));
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO.Response> obtenerPorRol(RolUsuario rol) {
        return usuarioRepository.findByRol(rol)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO.Response> obtenerPorEstado(EstadoUsuario estado) {
        return usuarioRepository.findByEstado(estado)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    //
    public UsuarioDTO.Response actualizar(Long id, UsuarioDTO.Request request) {
        logger.info("Actualizando usuario con id: " + id);
        Usuario usuario = buscarPorId(id);

        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            usuario.setPassword(request.getPassword());
        }
        if (request.getRol() != null) usuario.setRol(request.getRol());

        return mapearAResponse(usuarioRepository.save(usuario));
    }

    public UsuarioDTO.Response cambiarEstado(Long id, EstadoUsuario nuevoEstado) {
        logger.info("Cambiando estado del usuario " + id + " a " + nuevoEstado);
        Usuario usuario = buscarPorId(id);
        usuario.setEstado(nuevoEstado);
        return mapearAResponse(usuarioRepository.save(usuario));
    }

    //
    public void eliminar(Long id) {
        logger.info("Eliminando usuario con id: " + id);
        buscarPorId(id);
        usuarioRepository.deleteById(id);
        logger.info("Usuario " + id + " eliminado");
    }

    //
    private Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
    }

    private UsuarioDTO.Response mapearAResponse(Usuario usuario) {
        UsuarioDTO.Response response = new UsuarioDTO.Response();
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setEmail(usuario.getEmail());
        response.setRol(usuario.getRol());
        response.setEstado(usuario.getEstado());
        response.setCreadoEn(usuario.getCreadoEn());
        response.setActualizadoEn(usuario.getActualizadoEn());
        return response;
    }
}
