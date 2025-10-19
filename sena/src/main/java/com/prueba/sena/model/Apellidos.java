package com.prueba.sena.model;

import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apellidos {

    @Field("apellido1")
    private String apellido1;

    @Field("apellido2")
    private String apellido2;
    
    // Constructor personalizado
    public Apellidos(String apellido1) {
        this.apellido1 = apellido1;
        this.apellido2 = null;
    }
}