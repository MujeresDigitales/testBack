package com.prueba.sena.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    private String id;
    
    @Field("legacy_id")
    private Integer legacyId;

    @Field("nombre")
    private String nombre;

    @Field("descripcion")
    private String descripcion;

    @Field("orden")
    private Integer orden;

    @Field("activo")
    private Boolean activo = true;
    
    // Constructor de conveniencia
    public Group(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = true;
    }
}