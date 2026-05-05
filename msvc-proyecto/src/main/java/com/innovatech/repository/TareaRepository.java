package com.innovatech.repository;

import com.innovatech.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findByProyectoId(Long proyectoId);

    List<Tarea> findByEstado(String estado);

    List<Tarea> findByTituloContainingIgnoreCase(String titulo);
}