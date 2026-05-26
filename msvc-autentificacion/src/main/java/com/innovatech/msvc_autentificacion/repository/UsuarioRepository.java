package com.innovatech.msvc_autentificacion.repository;

import com.innovatech.msvc_autentificacion.model.EstadoUsuario;
import com.innovatech.msvc_autentificacion.model.RolUsuario;
import com.innovatech.msvc_autentificacion.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Usuario> findByRol(RolUsuario rol);
    List<Usuario> findByEstado(EstadoUsuario estado);
}
