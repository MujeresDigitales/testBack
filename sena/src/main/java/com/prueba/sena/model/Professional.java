package com.prueba.sena.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "professionals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professional {

    @Id
    private String id;
    
    @Field("legacy_id")
    private Integer legacyId;

    @Field("codigo")
    @Indexed(unique = true)
    private String codigo;

    @Field("id_persona")
    private Integer idPersona;

    @Field("registro_medico")
    private String registroMedico;

    @Field("id_tipo_prof")
    private Integer idTipoProf;
    
    // Constructor personalizado
    public Professional(String codigo, Integer idPersona, String registroMedico, Integer idTipoProf) {
        this.codigo = codigo;
        this.idPersona = idPersona;
        this.registroMedico = registroMedico;
        this.idTipoProf = idTipoProf;
    }
}