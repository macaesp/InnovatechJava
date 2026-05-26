package com.innovatech.msvc_recursos.repository;

import com.innovatech.msvc_recursos.model.EstadoRecurso;
import com.innovatech.msvc_recursos.model.Recurso;
import com.innovatech.msvc_recursos.model.TipoRecurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long>{

    List<Recurso> findByEstado(EstadoRecurso estado);
    List<Recurso> findByTipo(TipoRecurso tipo);
    List<Recurso> findByProyectoId(Long proyectoId);
    List<Recurso> findByNombreContainingIgnoreCase(String nombre);
}

