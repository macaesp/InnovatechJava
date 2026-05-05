package com.innovatech.repository;

import com.innovatech.model.Colaboracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColaboracionRepository extends JpaRepository<Colaboracion, Long> {

    // Métodos personalizados que usará nuestro Service
    List<Colaboracion> findByProyectoId(Long proyectoId);

    List<Colaboracion> findByEstado(String estado);

    List<Colaboracion> findByRolAsignadoContainingIgnoreCase(String rolAsignado);
}