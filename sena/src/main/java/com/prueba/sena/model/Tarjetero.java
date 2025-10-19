package com.prueba.sena.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Document(collection = "tarjeteros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarjetero {

    @Id
    private String id;
    
    @Field("legacy_id")
    private Integer legacyId;

    @Field("person_legacy_id")
    private Integer personLegacyId;

    @Field("historia")
    private String historia;

    @Field("id_eps")
    private Integer idEps;

    @Field("id_nivel")
    private Integer idNivel;

    @Field("fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Field("activo")
    private Boolean activo;
    
    // Constructor personalizado
    public Tarjetero(String historia, Integer idEps, Integer idNivel, Boolean activo) {
        this.historia = historia;
        this.idEps = idEps;
        this.idNivel = idNivel;
        this.activo = activo;
    }
}