package com.prueba.sena.model;

import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResult {

    @Field("legacy_id")
    private Integer legacyId;

    @Field("fecha")
    private LocalDateTime fecha;

    @Field("id_prueba")
    private Integer idPrueba;

    @Field("res_numerico")
    private String resNumerico;

    @Field("res_texto")
    private String resTexto;

    @Field("res_memo")
    private String resMemo;
    
    // Constructor personalizado
    public OrderResult(Integer idPrueba, String resNumerico, String resTexto, String resMemo) {
        this.idPrueba = idPrueba;
        this.resNumerico = resNumerico;
        this.resTexto = resTexto;
        this.resMemo = resMemo;
        this.fecha = LocalDateTime.now();
    }
}