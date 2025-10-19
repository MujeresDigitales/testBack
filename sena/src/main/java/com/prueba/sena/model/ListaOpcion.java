package com.prueba.sena.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "lista_opcion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaOpcion {

    @Id
    private String id;
    
    @Field("legacy_id")
    private Integer legacyId;

    @Field("variable")
    private String variable;

    @Field("descripcion")
    private String descripcion;

    @Field("valor")
    private Integer valor;

    @Field("nombre")
    private String nombre;

    @Field("abreviacion")
    private String abreviacion;

    @Field("habilita")
    private Boolean habilita;
    
    // Constructor personalizado
    public ListaOpcion(String variable, String descripcion, Integer valor, String nombre) {
        this.variable = variable;
        this.descripcion = descripcion;
        this.valor = valor;
        this.nombre = nombre;
        this.abreviacion = "";
        this.habilita = true;
    }
}