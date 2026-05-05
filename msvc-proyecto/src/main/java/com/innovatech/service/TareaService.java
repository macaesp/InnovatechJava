package com.innovatech.service;

import com.innovatech.model.Proyecto;
import com.innovatech.model.Tarea;
import com.innovatech.repository.ProyectoRepository;
import com.innovatech.repository.TareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TareaService {

    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;

    public Tarea crearTarea(Long proyectoId, Tarea tarea) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + proyectoId));

        tarea.setProyecto(proyecto);

        return tareaRepository.save(tarea);
    }

    public List<Tarea> listarTareas() {
        return tareaRepository.findAll();
    }

    public List<Tarea> listarTareasPorProyecto(Long proyectoId) {
        if (!proyectoRepository.existsById(proyectoId)) {
            throw new RuntimeException("Proyecto no encontrado con id: " + proyectoId);
        }

        return tareaRepository.findByProyectoId(proyectoId);
    }

    public Tarea obtenerTareaPorId(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));
    }

    public Tarea actualizarTarea(Long id, Tarea tareaActualizada) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));

        tarea.setTitulo(tareaActualizada.getTitulo());
        tarea.setDescripcion(tareaActualizada.getDescripcion());
        tarea.setEstado(tareaActualizada.getEstado());
        tarea.setFechaInicio(tareaActualizada.getFechaInicio());
        tarea.setFechaFin(tareaActualizada.getFechaFin());

        return tareaRepository.save(tarea);
    }

    public void eliminarTarea(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new RuntimeException("Tarea no encontrada con id: " + id);
        }

        tareaRepository.deleteById(id);
    }

    public List<Tarea> buscarPorEstado(String estado) {
        return tareaRepository.findByEstado(estado);
    }

    public List<Tarea> buscarPorTitulo(String titulo) {
        return tareaRepository.findByTituloContainingIgnoreCase(titulo);
    }
}