package com.prueba.sena.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "cups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cups {

    @Id
    private String id;
    
    @Field("legacy_id")
    private Integer legacyId;

    @Field("codigo")
    @Indexed(unique = true)
    private String codigo;

    @Field("nombre")
    private String nombre;

    @Field("habilita")
    private Boolean habilita;

    // Constructor personalizado
    public Cups(String codigo, String nombre, Boolean habilita) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.habilita = habilita;
    }
}
