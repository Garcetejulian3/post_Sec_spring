package com.security_base.sc_base.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
public class Posteos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPost;
    private String titulo;
    private String contenido;
    private String tema;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserProfile author;
}
