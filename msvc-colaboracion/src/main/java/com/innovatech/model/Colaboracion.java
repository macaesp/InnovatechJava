package com.innovatech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "colaboraciones")
public class Colaboracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del proyecto no puede ser nulo")
    private Long proyectoId;

    @NotNull(message = "El ID del recurso no puede ser nulo")
    private Long recursoId;

    @NotBlank(message = "El rol asignado es obligatorio")
    private String rolAsignado;

    private String estado = "ACTIVO";

    // Constructor vacío exigido por JPA
    public Colaboracion() {
    }

    // Constructor con parámetros
    public Colaboracion(Long id, Long proyectoId, Long recursoId, String rolAsignado, String estado) {
        this.id = id;
        this.proyectoId = proyectoId;
        this.recursoId = recursoId;
        this.rolAsignado = rolAsignado;
        this.estado = estado;
    }

    // --- GETTERS Y SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    public Long getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(Long recursoId) {
        this.recursoId = recursoId;
    }

    public String getRolAsignado() {
        return rolAsignado;
    }

    public void setRolAsignado(String rolAsignado) {
        this.rolAsignado = rolAsignado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}