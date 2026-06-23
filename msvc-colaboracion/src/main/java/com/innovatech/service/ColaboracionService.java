package com.innovatech.service;

import com.innovatech.exception.RecursoNoEncontradoException;
import com.innovatech.model.Colaboracion;
import com.innovatech.repository.ColaboracionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaboracionService {

    private final ColaboracionRepository colaboracionRepository;

    public ColaboracionService(ColaboracionRepository colaboracionRepository) {
        this.colaboracionRepository = colaboracionRepository;
    }

    public Colaboracion crearColaboracion(Colaboracion colaboracion) {
        return colaboracionRepository.save(colaboracion);
    }

    public Colaboracion crearColaboracion(Long proyectoId, Colaboracion colaboracion) {
        colaboracion.setProyectoId(proyectoId);
        return colaboracionRepository.save(colaboracion);
    }

    public List<Colaboracion> listarColaboraciones() {
        return colaboracionRepository.findAll();
    }

    public Colaboracion obtenerColaboracionPorId(Long id) {
        return colaboracionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Colaboración no encontrada con ID: " + id));
    }

    public List<Colaboracion> listarColaboracionesPorProyecto(Long proyectoId) {
        return colaboracionRepository.findByProyectoId(proyectoId);
    }

    public Colaboracion actualizarColaboracion(Long id, Colaboracion colaboracionActualizada) {
        Colaboracion colaboracionExistente = obtenerColaboracionPorId(id);

        // Actualizamos los datos
        colaboracionExistente.setProyectoId(colaboracionActualizada.getProyectoId());
        colaboracionExistente.setRecursoId(colaboracionActualizada.getRecursoId());
        colaboracionExistente.setRolAsignado(colaboracionActualizada.getRolAsignado());
        colaboracionExistente.setEstado(colaboracionActualizada.getEstado());

        return colaboracionRepository.save(colaboracionExistente);
    }

    public void eliminarColaboracion(Long id) {
        Colaboracion colaboracion = obtenerColaboracionPorId(id);
        colaboracionRepository.delete(colaboracion);
    }

    public List<Colaboracion> buscarPorEstado(String estado) {
        return colaboracionRepository.findByEstado(estado);
    }

    public List<Colaboracion> buscarPorRolAsignado(String rolAsignado) {
        return colaboracionRepository.findByRolAsignadoContainingIgnoreCase(rolAsignado);
    }

    public List<Colaboracion> buscarPorNombre(String nombre) {
        return List.of();
    }
}