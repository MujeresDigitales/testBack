package com.prueba.sena.repository;

import com.prueba.sena.model.Tarjetero;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TarjeteroRepository extends MongoRepository<Tarjetero, String> {
    
    // Buscar tarjetero(s) de un paciente (puede tener múltiples historias)
    List<Tarjetero> findByPersonLegacyId(Integer personLegacyId);
    
    // Buscar por legacy_id específico
    Tarjetero findByLegacyId(Integer legacyId);
    
    // Buscar por historia específica
    Tarjetero findByHistoria(String historia);
}