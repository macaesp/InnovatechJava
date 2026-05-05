package com.innovatech.dto;

import com.innovatech.model.Proyecto;
import com.innovatech.model.Tarea;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * DTOs (Data Transfer Objects) para Proyecto y Tarea.
 * Separan la capa de presentación del modelo de dominio.
 */
public class ProyectoDTO {

    // ─── REQUEST DTOs (lo que llega desde el cliente) ───────────────────────

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProyectoRequest {
        @NotBlank(message = "El nombre es obligatorio")
        private String nombre;

        private String descripcion;

        @NotNull(message = "La fecha de inicio es obligatoria")
        private LocalDate fechaInicio;

        private LocalDate fechaFinEstimada;

        private Long gerenteId;

        private Double presupuesto;

        private Proyecto.EstadoProyecto estado;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TareaRequest {
        @NotBlank(message = "El título es obligatorio")
        private String titulo;

        private String descripcion;

        @NotNull(message = "La fecha de inicio es obligatoria")
        private LocalDate fechaInicio;

        private LocalDate fechaLimite;

        private Long responsableId;

        private Tarea.EstadoTarea estado;

        private Tarea.Prioridad prioridad;
    }

    // ─── RESPONSE DTOs (lo que devuelve la API) ──────────────────────────────

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProyectoResponse {
        private Long id;
        private String nombre;
        private String descripcion;
        private LocalDate fechaInicio;
        private LocalDate fechaFinEstimada;
        private LocalDate fechaFinReal;
        private Proyecto.EstadoProyecto estado;
        private Long gerenteId;
        private Double presupuesto;
        private int totalTareas;
        private long tareasCompletadas;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProyectoDetalleResponse {
        private Long id;
        private String nombre;
        private String descripcion;
        private LocalDate fechaInicio;
        private LocalDate fechaFinEstimada;
        private LocalDate fechaFinReal;
        private Proyecto.EstadoProyecto estado;
        private Long gerenteId;
        private Double presupuesto;
        private List<TareaResponse> tareas;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TareaResponse {
        private Long id;
        private String titulo;
        private String descripcion;
        private Tarea.EstadoTarea estado;
        private Tarea.Prioridad prioridad;
        private Long responsableId;
        private LocalDate fechaInicio;
        private LocalDate fechaLimite;
        private LocalDate fechaCompletado;
        private Long proyectoId;
    }
}
