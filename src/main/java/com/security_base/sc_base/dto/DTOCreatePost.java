package com.security_base.sc_base.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class DTOCreatePost {
    private String titulo;
    private String contenido;
    private String tema;
}
