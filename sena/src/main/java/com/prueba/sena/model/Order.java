package com.prueba.sena.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;
    
    @Field("legacy_id")
    private Integer legacyId;

    @Field("fecha")
    private LocalDateTime fecha;

    @Field("historia_id")
    private Integer historiaId;

    @Field("profesional_ordena_id")
    private Integer profesionalOrdenaId;

    @Field("profesional_externo")
    private Boolean profesionalExterno;

    @Field("results")
    private List<OrderResult> results;
    
    // Constructor personalizado
    public Order(Integer historiaId, Integer profesionalOrdenaId, Boolean profesionalExterno) {
        this.historiaId = historiaId;
        this.profesionalOrdenaId = profesionalOrdenaId;
        this.profesionalExterno = profesionalExterno;
        this.fecha = LocalDateTime.now();
    }
}