package com.innovatech.service;

import com.innovatech.model.Proyecto;
import com.innovatech.repository.ProyectoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public Proyecto crearProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAll();
    }

    public Proyecto obtenerProyectoPorId(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + id));
    }

    public Proyecto actualizarProyecto(Long id, Proyecto proyectoActualizado) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + id));

        proyecto.setNombre(proyectoActualizado.getNombre());
        proyecto.setDescripcion(proyectoActualizado.getDescripcion());
        proyecto.setFechaInicio(proyectoActualizado.getFechaInicio());
        proyecto.setFechaFin(proyectoActualizado.getFechaFin());
        proyecto.setEstado(proyectoActualizado.getEstado());

        return proyectoRepository.save(proyecto);
    }

    public void eliminarProyecto(Long id) {
        if (!proyectoRepository.existsById(id)) {
            throw new RuntimeException("Proyecto no encontrado con id: " + id);
        }

        proyectoRepository.deleteById(id);
    }

    public List<Proyecto> buscarPorEstado(String estado) {
        return proyectoRepository.findByEstado(estado);
    }

    public List<Proyecto> buscarPorNombre(String nombre) {
        return proyectoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}