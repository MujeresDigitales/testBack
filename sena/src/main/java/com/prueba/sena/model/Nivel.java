package com.prueba.sena.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "niveles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nivel {

    @Id
    private String id;
    
    @Field("legacy_id")
    private Integer legacyId;

    @Field("id_eps")
    private Integer idEps;

    @Field("nivel")
    private String nivel;

    @Field("nombre")
    private String nombre;

    @Field("id_regimen")
    private Integer idRegimen;
    
    // Constructor personalizado
    public Nivel(Integer idEps, String nivel, String nombre, Integer idRegimen) {
        this.idEps = idEps;
        this.nivel = nivel;
        this.nombre = nombre;
        this.idRegimen = idRegimen;
    }
}