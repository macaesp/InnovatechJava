package com.innovatech.msvc_proyecto.service;

import com.innovatech.msvc_proyecto.dto.ProyectoDTO;
import com.innovatech.msvc_proyecto.exception.ProyectoNoEncontradoException;
import com.innovatech.msvc_proyecto.model.EstadoProyecto;
import com.innovatech.msvc_proyecto.model.Proyecto;
import com.innovatech.msvc_proyecto.repository.ProyectoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProyectoService {

    private static final Logger logger = Logger.getLogger(ProyectoService.class.getName());

    private final ProyectoRepository proyectoRepository;
    private final NotificacionService notificacionService;

    public ProyectoService(ProyectoRepository proyectoRepository, NotificacionService notificacionService) {
        this.proyectoRepository = proyectoRepository;
        this.notificacionService = notificacionService;
    }

    public ProyectoDTO.Response crear(ProyectoDTO.Request request) {
        logger.info("Creando nuevo proyecto: " + request.getNombre());

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(request.getNombre());
        proyecto.setDescripcion(request.getDescripcion());
        proyecto.setFechaInicio(request.getFechaInicio());
        proyecto.setFechaFin(request.getFechaFin());
        proyecto.setEstado(request.getEstado() != null ? request.getEstado() : EstadoProyecto.PLANIFICACION);
        proyecto.setResponsable(request.getResponsable());
        proyecto.setPresupuesto(request.getPresupuesto());

        Proyecto guardado = proyectoRepository.save(proyecto);
        logger.info("Proyecto creado con id: " + guardado.getId());
        return mapearAResponse(guardado);
    }

    @Transactional(readOnly = true)
    public List<ProyectoDTO.Response> obtenerTodos() {
        logger.info("Obteniendo todos los proyectos");
        return proyectoRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProyectoDTO.Response obtenerPorId(Long id) {
        logger.info("Buscando proyecto con id: " + id);
        return mapearAResponse(buscarPorId(id));
    }

    @Transactional(readOnly = true)
    public List<ProyectoDTO.Response> obtenerPorEstado(EstadoProyecto estado) {
        return proyectoRepository.findByEstado(estado)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProyectoDTO.Response> buscarPorNombre(String nombre) {
        return proyectoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public ProyectoDTO.Response actualizar(Long id, ProyectoDTO.Request request) {
        logger.info("Actualizando proyecto con id: " + id);
        Proyecto proyecto = buscarPorId(id);

        proyecto.setNombre(request.getNombre());
        proyecto.setDescripcion(request.getDescripcion());
        proyecto.setFechaInicio(request.getFechaInicio());
        proyecto.setFechaFin(request.getFechaFin());
        proyecto.setResponsable(request.getResponsable());
        proyecto.setPresupuesto(request.getPresupuesto());
        if (request.getEstado() != null) proyecto.setEstado(request.getEstado());

        return mapearAResponse(proyectoRepository.save(proyecto));
    }

    public ProyectoDTO.Response cambiarEstado(Long id, EstadoProyecto nuevoEstado) {
        logger.info("Cambiando estado del proyecto " + id + " a " + nuevoEstado);
        Proyecto proyecto = buscarPorId(id);
        EstadoProyecto estadoAnterior = proyecto.getEstado();

        proyecto.setEstado(nuevoEstado);
        Proyecto guardado = proyectoRepository.save(proyecto);

        notificacionService.notificarCambioDeEstado(guardado, estadoAnterior);

        return mapearAResponse(guardado);
    }

    public void eliminar(Long id) {
        logger.info("Eliminando proyecto con id: " + id);
        buscarPorId(id);
        proyectoRepository.deleteById(id);
        logger.info("Proyecto " + id + " eliminado");
    }

    private Proyecto buscarPorId(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new ProyectoNoEncontradoException(id));
    }

    private ProyectoDTO.Response mapearAResponse(Proyecto proyecto) {
        ProyectoDTO.Response response = new ProyectoDTO.Response();
        response.setId(proyecto.getId());
        response.setNombre(proyecto.getNombre());
        response.setDescripcion(proyecto.getDescripcion());
        response.setFechaInicio(proyecto.getFechaInicio());
        response.setFechaFin(proyecto.getFechaFin());
        response.setEstado(proyecto.getEstado());
        response.setResponsable(proyecto.getResponsable());
        response.setPresupuesto(proyecto.getPresupuesto());
        response.setCreadoEn(proyecto.getCreadoEn());
        response.setActualizadoEn(proyecto.getActualizadoEn());
        return response;
    }
}