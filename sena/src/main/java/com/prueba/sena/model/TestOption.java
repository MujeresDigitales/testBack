package com.prueba.sena.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "test_options")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestOption {

    @Id
    private String id;
    
    @Field("legacy_id")
    private Integer legacyId;

    @Field("id_prueba")
    private Integer idPrueba;

    @Field("opcion")
    private String opcion;

    @Field("valor_ref_min_f")
    private String valorRefMinF;

    @Field("valor_ref_max_f")
    private String valorRefMaxF;

    @Field("valor_ref_min_m")
    private String valorRefMinM;

    @Field("valor_ref_max_m")
    private String valorRefMaxM;
    
    // Constructor personalizado
    public TestOption(Integer idPrueba, String opcion, String valorRefMinF, String valorRefMaxF, 
                      String valorRefMinM, String valorRefMaxM) {
        this.idPrueba = idPrueba;
        this.opcion = opcion;
        this.valorRefMinF = valorRefMinF;
        this.valorRefMaxF = valorRefMaxF;
        this.valorRefMinM = valorRefMinM;
        this.valorRefMaxM = valorRefMaxM;
    }
}