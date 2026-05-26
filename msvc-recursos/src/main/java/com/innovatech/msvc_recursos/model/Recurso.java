package com.innovatech.msvc_recursos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name= "recursos")
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del recurso es obligatorio")
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El tipo de recurso es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 30)
    private TipoRecurso tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private EstadoRecurso estado = EstadoRecurso.DISPONIBLE;

    @Column(name = "proyecto_id")
    private Long proyectoId;

    @Column(name = "responsable", length = 100)
    private String responsable;

    @Column(name = "costo_por_hora")
    private Double costoPorHora;

    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @PrePersist
    protected void onCreate() {
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.actualizadoEn = LocalDateTime.now();
    }

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

