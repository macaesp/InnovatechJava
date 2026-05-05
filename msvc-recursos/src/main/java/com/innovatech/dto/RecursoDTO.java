package com.innovatech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecursoDTO {

    private String nombre;
    private String rol;
    private String email;
    private String estado;
}