package com.innovatech.msvc_recursos.service;

import com.innovatech.msvc_recursos.dto.RecursoDTO;
import com.innovatech.msvc_recursos.exception.RecursoNoEncontradoException;
import com.innovatech.msvc_recursos.model.*;
import com.innovatech.msvc_recursos.repository.RecursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecursoService {

    private static final Logger logger = Logger.getLogger(RecursoService.class.getName());

    private final RecursoRepository recursoRepository;

    public RecursoService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    public RecursoDTO.Response crear(RecursoDTO.Request request) {
        logger.info("Creando nuevo recurso: " + request.getNombre());

        Recurso recurso = new Recurso();
        recurso.setNombre(request.getNombre());
        recurso.setDescripcion(request.getDescripcion());
        recurso.setTipo(request.getTipo());
        recurso.setEstado(request.getEstado() != null ? request.getEstado() : EstadoRecurso.DISPONIBLE);
        recurso.setProyectoId(request.getProyectoId());
        recurso.setResponsable(request.getResponsable());
        recurso.setCostoPorHora(request.getCostoPorHora());

        Recurso guardado = recursoRepository.save(recurso);
        logger.info("Recurso creado con id: " + guardado.getId());
        return mapearAResponse(guardado);
    }

    @Transactional(readOnly = true)
    public List<RecursoDTO.Response> obtenerTodos() {
        logger.info("Obteniendo todos los recursos");
        return recursoRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RecursoDTO.Response obtenerPorId(Long id) {
        logger.info("Buscando recurso con id: " + id);
        return mapearAResponse(buscarPorId(id));
    }

    @Transactional(readOnly = true)
    public List<RecursoDTO.Response> obtenerPorEstado(EstadoRecurso estado) {
        return recursoRepository.findByEstado(estado)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecursoDTO.Response> obtenerPorTipo(TipoRecurso tipo) {
        return recursoRepository.findByTipo(tipo)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecursoDTO.Response> obtenerPorProyecto(Long proyectoId) {
        return recursoRepository.findByProyectoId(proyectoId)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecursoDTO.Response> buscarPorNombre(String nombre) {
        return recursoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public RecursoDTO.Response actualizar(Long id, RecursoDTO.Request request) {
        logger.info("Actualizando recurso con id: " + id);
        Recurso recurso = buscarPorId(id);

        recurso.setNombre(request.getNombre());
        recurso.setDescripcion(request.getDescripcion());
        recurso.setTipo(request.getTipo());
        recurso.setProyectoId(request.getProyectoId());
        recurso.setResponsable(request.getResponsable());
        recurso.setCostoPorHora(request.getCostoPorHora());
        if (request.getEstado() != null) recurso.setEstado(request.getEstado());

        return mapearAResponse(recursoRepository.save(recurso));
    }

    public RecursoDTO.Response cambiarEstado(Long id, EstadoRecurso nuevoEstado) {
        logger.info("Cambiando estado del recurso " + id + " a " + nuevoEstado);
        Recurso recurso = buscarPorId(id);
        recurso.setEstado(nuevoEstado);
        return mapearAResponse(recursoRepository.save(recurso));
    }

    public void eliminar(Long id) {
        logger.info("Eliminando recurso con id: " + id);
        buscarPorId(id);
        recursoRepository.deleteById(id);
        logger.info("Recurso " + id + " eliminado");
    }

    private Recurso buscarPorId(Long id) {
        return recursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Recurso", id));
    }

    private RecursoDTO.Response mapearAResponse(Recurso recurso) {
        RecursoDTO.Response response = new RecursoDTO.Response();
        response.setId(recurso.getId());
        response.setNombre(recurso.getNombre());
        response.setDescripcion(recurso.getDescripcion());
        response.setTipo(recurso.getTipo());
        response.setEstado(recurso.getEstado());
        response.setProyectoId(recurso.getProyectoId());
        response.setResponsable(recurso.getResponsable());
        response.setCostoPorHora(recurso.getCostoPorHora());
        response.setCreadoEn(recurso.getCreadoEn());
        response.setActualizadoEn(recurso.getActualizadoEn());
        return response;
    }
}
