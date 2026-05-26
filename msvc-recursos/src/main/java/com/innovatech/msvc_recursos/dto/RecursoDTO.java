package com.innovatech.msvc_recursos.dto;

import com.innovatech.msvc_recursos.model.EstadoRecurso;
import com.innovatech.msvc_recursos.model.TipoRecurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class RecursoDTO {

    public static class Request {

        @NotBlank(message = "El nombre del recurso es obligatorio")
        private String nombre;

        private String descripcion;

        @NotNull(message = "El tipo de recurso es obligatorio")
        private TipoRecurso tipo;

        private EstadoRecurso estado;
        private Long proyectoId;
        private String responsable;
        private Double costoPorHora;

        public String getNombre() { return nombre; }
        public String getDescripcion() { return descripcion; }
        public TipoRecurso getTipo() { return tipo; }
        public EstadoRecurso getEstado() { return estado; }
        public Long getProyectoId() { return proyectoId; }
        public String getResponsable() { return responsable; }
        public Double getCostoPorHora() { return costoPorHora; }

        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public void setTipo(TipoRecurso tipo) { this.tipo = tipo; }
        public void setEstado(EstadoRecurso estado) { this.estado = estado; }
        public void setProyectoId(Long proyectoId) { this.proyectoId = proyectoId; }
        public void setResponsable(String responsable) { this.responsable = responsable; }
        public void setCostoPorHora(Double costoPorHora) { this.costoPorHora = costoPorHora; }
    }

    public static class Response {

        private Long id;
        private String nombre;
        private String descripcion;
        private TipoRecurso tipo;
        private EstadoRecurso estado;
        private Long proyectoId;
        private String responsable;
        private Double costoPorHora;
        private LocalDateTime creadoEn;
        private LocalDateTime actualizadoEn;


        public Long getId() { return id; }
        public String getNombre() { return nombre; }
        public String getDescripcion() { return descripcion; }
        public TipoRecurso getTipo() { return tipo; }
        public EstadoRecurso getEstado() { return estado; }
        public Long getProyectoId() { return proyectoId; }
        public String getResponsable() { return responsable; }
        public Double getCostoPorHora() { return costoPorHora; }
        public LocalDateTime getCreadoEn() { return creadoEn; }
        public LocalDateTime getActualizadoEn() { return actualizadoEn; }

        public void setId(Long id) { this.id = id; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public void setTipo(TipoRecurso tipo) { this.tipo = tipo; }
        public void setEstado(EstadoRecurso estado) { this.estado = estado; }
        public void setProyectoId(Long proyectoId) { this.proyectoId = proyectoId; }
        public void setResponsable(String responsable) { this.responsable = responsable; }
        public void setCostoPorHora(Double costoPorHora) { this.costoPorHora = costoPorHora; }
        public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
        public void setActualizadoEn(LocalDateTime actualizadoEn) { this.actualizadoEn = actualizadoEn; }
    }
}