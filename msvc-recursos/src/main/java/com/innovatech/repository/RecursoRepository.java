package com.innovatech.repository;

import com.innovatech.model.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long> {

    List<Recurso> findByEstado(String estado);

    List<Recurso> findByRol(String rol);

    List<Recurso> findByNombreContainingIgnoreCase(String nombre);
}