package com.innovatech.service;

import com.innovatech.model.Recurso;
import com.innovatech.repository.RecursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecursoService {

    private final RecursoRepository recursoRepository;

    public Recurso crearRecurso(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    public List<Recurso> listarRecursos() {
        return recursoRepository.findAll();
    }

    public Recurso obtenerRecursoPorId(Long id) {
        return recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado con id: " + id));
    }

    public Recurso actualizarRecurso(Long id, Recurso recursoActualizado) {
        Recurso recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado con id: " + id));

        recurso.setNombre(recursoActualizado.getNombre());
        recurso.setRol(recursoActualizado.getRol());
        recurso.setEmail(recursoActualizado.getEmail());
        recurso.setEstado(recursoActualizado.getEstado());

        return recursoRepository.save(recurso);
    }

    public void eliminarRecurso(Long id) {
        if (!recursoRepository.existsById(id)) {
            throw new RuntimeException("Recurso no encontrado con id: " + id);
        }

        recursoRepository.deleteById(id);
    }

    public List<Recurso> buscarPorEstado(String estado) {
        return recursoRepository.findByEstado(estado);
    }

    public List<Recurso> buscarPorRol(String rol) {
        return recursoRepository.findByRol(rol);
    }

    public List<Recurso> buscarPorNombre(String nombre) {
        return recursoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}