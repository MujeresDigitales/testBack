package com.prueba.sena.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "eps")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Eps {

    @Id
    private String id;
    
    @Field("legacy_id")
    private Integer legacyId;

    @Field("codigo")
    @Indexed(unique = true)
    private String codigo;

    @Field("razonsocial")
    private String razonSocial;

    @Field("nit")
    private String nit;

    @Field("habilita")
    private Boolean habilita;
    
    // Constructor personalizado
    public Eps(String codigo, String razonSocial, String nit, Boolean habilita) {
        this.codigo = codigo;
        this.razonSocial = razonSocial;
        this.nit = nit;
        this.habilita = habilita;
    }
}