package com.prueba.sena.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "tests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test {

    @Id
    private String id;
    
    @Field("legacy_id")
    private Integer legacyId;

    @Field("id_procedimiento")
    private Integer idProcedimiento;

    @Field("codigo_prueba")
    @Indexed
    private String codigoPrueba;

    @Field("nombre_prueba")
    private String nombrePrueba;

    @Field("id_tipo_resultado")
    private Integer idTipoResultado;

    @Field("unidad")
    private String unidad;

    @Field("habilita")
    private Boolean habilita;

    @Field("grupo_id")
    private Integer grupoId;
    
    // Constructor personalizado
    public Test(Integer idProcedimiento, String codigoPrueba, String nombrePrueba, Integer idTipoResultado) {
        this.idProcedimiento = idProcedimiento;
        this.codigoPrueba = codigoPrueba;
        this.nombrePrueba = nombrePrueba;
        this.idTipoResultado = idTipoResultado;
        this.unidad = "";
        this.habilita = true;
    }
}