package com.prueba.sena.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Document(collection = "persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    private String id;
    
    @Field("legacy_id")
    private Integer legacyId;

    @Field("tipo_identificacion_id")
    private Integer tipoIdentificacionId;

    @Field("numeroid")
    @Indexed
    private String numeroId;

    @Field("apellidos")
    private Apellidos apellidos;

    @Field("nombres")
    private Nombres nombres;

    @Field("fechanac")
    private LocalDate fechaNac;

    @Field("sexo_id")
    private Integer sexoId;

    @Field("direccion")
    private String direccion;

    @Field("tel_movil")
    private String telMovil;

    @Field("email")
    private String email;
    
    // Constructor personalizado
    public Person(String numeroId, Apellidos apellidos, Nombres nombres, LocalDate fechaNac) {
        this.numeroId = numeroId;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.fechaNac = fechaNac;
    }
}
