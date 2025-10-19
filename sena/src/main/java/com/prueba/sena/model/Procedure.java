package com.prueba.sena.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "procedures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Procedure {

    @Id
    private String id;
    
    @Field("legacy_id")
    private Integer legacyId;

    @Field("id_cups")
    private Integer idCups;

    @Field("id_grupo_laboratorio")
    private Integer idGrupoLaboratorio;

    @Field("metodo")
    private String metodo;
    
    // Constructor personalizado
    public Procedure(Integer idCups, Integer idGrupoLaboratorio, String metodo) {
        this.idCups = idCups;
        this.idGrupoLaboratorio = idGrupoLaboratorio;
        this.metodo = metodo;
    }
}