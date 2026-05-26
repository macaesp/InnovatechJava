package com.innovatech.msvc_autentificacion.dto;

import com.innovatech.msvc_autentificacion.model.EstadoUsuario;
import com.innovatech.msvc_autentificacion.model.RolUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class UsuarioDTO {
    public static class Request {

        @NotBlank(message = "El nombre es obligatorio")
        private String nombre;

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no es válido")
        private String email;

        @NotBlank(message = "La contraseña es obligatoria")
        private String password;

        private RolUsuario rol;

        // Getters
        public String getNombre() { return nombre; }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public RolUsuario getRol() { return rol; }

        // Setters
        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setEmail(String email) { this.email = email; }
        public void setPassword(String password) { this.password = password; }
        public void setRol(RolUsuario rol) { this.rol = rol; }
    }

    // REQUEST - Login
    public static class LoginRequest {

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no es válido")
        private String email;

        @NotBlank(message = "La contraseña es obligatoria")
        private String password;

        // Getters
        public String getEmail() { return email; }
        public String getPassword() { return password; }

        // Setters
        public void setEmail(String email) { this.email = email; }
        public void setPassword(String password) { this.password = password; }
    }


    // RESPONSE
    public static class Response {

        private Long id;
        private String nombre;
        private String email;
        private RolUsuario rol;
        private EstadoUsuario estado;
        private LocalDateTime creadoEn;
        private LocalDateTime actualizadoEn;

        // Getters
        public Long getId() { return id; }
        public String getNombre() { return nombre; }
        public String getEmail() { return email; }
        public RolUsuario getRol() { return rol; }
        public EstadoUsuario getEstado() { return estado; }
        public LocalDateTime getCreadoEn() { return creadoEn; }
        public LocalDateTime getActualizadoEn() { return actualizadoEn; }

        // Setters
        public void setId(Long id) { this.id = id; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setEmail(String email) { this.email = email; }
        public void setRol(RolUsuario rol) { this.rol = rol; }
        public void setEstado(EstadoUsuario estado) { this.estado = estado; }
        public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
        public void setActualizadoEn(LocalDateTime actualizadoEn) { this.actualizadoEn = actualizadoEn; }
    }


    // RESPONSE - Login exitoso
    public static class LoginResponse {

        private String mensaje;
        private Long id;
        private String nombre;
        private String email;
        private RolUsuario rol;

        // Getters
        public String getMensaje() { return mensaje; }
        public Long getId() { return id; }
        public String getNombre() { return nombre; }
        public String getEmail() { return email; }
        public RolUsuario getRol() { return rol; }

        // Setters
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }
        public void setId(Long id) { this.id = id; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setEmail(String email) { this.email = email; }
        public void setRol(RolUsuario rol) { this.rol = rol; }
    }
}
