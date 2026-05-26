package com.innovatech.msvc_proyecto.dto;

import com.innovatech.msvc_proyecto.model.EstadoProyecto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProyectoDTO {

    public static class Request {

        @NotBlank(message = "El nombre del proyecto es obligatorio")
        private String nombre;

        private String descripcion;

        @NotNull(message = "La fecha de inicio es obligatoria")
        private LocalDate fechaInicio;

        private LocalDate fechaFin;

        private EstadoProyecto estado;

        @NotBlank(message = "El responsable es obligatorio")
        private String responsable;

        private Double presupuesto;


        public String getNombre() { return nombre; }
        public String getDescripcion() { return descripcion; }
        public LocalDate getFechaInicio() { return fechaInicio; }
        public LocalDate getFechaFin() { return fechaFin; }
        public EstadoProyecto getEstado() { return estado; }
        public String getResponsable() { return responsable; }
        public Double getPresupuesto() { return presupuesto; }

        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
        public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
        public void setEstado(EstadoProyecto estado) { this.estado = estado; }
        public void setResponsable(String responsable) { this.responsable = responsable; }
        public void setPresupuesto(Double presupuesto) { this.presupuesto = presupuesto; }
    }


    public static class Response {

        private Long id;
        private String nombre;
        private String descripcion;
        private LocalDate fechaInicio;
        private LocalDate fechaFin;
        private EstadoProyecto estado;
        private String responsable;
        private Double presupuesto;
        private LocalDateTime creadoEn;
        private LocalDateTime actualizadoEn;


        public Long getId() { return id; }
        public String getNombre() { return nombre; }
        public String getDescripcion() { return descripcion; }
        public LocalDate getFechaInicio() { return fechaInicio; }
        public LocalDate getFechaFin() { return fechaFin; }
        public EstadoProyecto getEstado() { return estado; }
        public String getResponsable() { return responsable; }
        public Double getPresupuesto() { return presupuesto; }
        public LocalDateTime getCreadoEn() { return creadoEn; }
        public LocalDateTime getActualizadoEn() { return actualizadoEn; }


        public void setId(Long id) { this.id = id; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
        public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
        public void setEstado(EstadoProyecto estado) { this.estado = estado; }
        public void setResponsable(String responsable) { this.responsable = responsable; }
        public void setPresupuesto(Double presupuesto) { this.presupuesto = presupuesto; }
        public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
        public void setActualizadoEn(LocalDateTime actualizadoEn) { this.actualizadoEn = actualizadoEn; }
    }
}