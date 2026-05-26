package com.innovatech.msvc_proyecto.repository;

import com.innovatech.msvc_proyecto.model.EstadoProyecto;
import com.innovatech.msvc_proyecto.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    List<Proyecto> findByEstado(EstadoProyecto estado);

    List<Proyecto> findByResponsable(String responsable);

    List<Proyecto> findByNombreContainingIgnoreCase(String nombre);
}