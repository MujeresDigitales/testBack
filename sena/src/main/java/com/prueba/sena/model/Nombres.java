package com.prueba.sena.model;

import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nombres {

    @Field("nombre1")
    private String nombre1;

    @Field("nombre2")
    private String nombre2;
    
    // Constructor personalizado
    public Nombres(String nombre1) {
        this.nombre1 = nombre1;
        this.nombre2 = null;
    }
}