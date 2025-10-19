package com.prueba.sena.repository;

import com.prueba.sena.model.Professional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalRepository extends MongoRepository<Professional, String> {
    
    // Buscar profesional que ordenó el examen
    Professional findByLegacyId(Integer legacyId);
    
    // Buscar por código de profesional
    Professional findByCodigo(String codigo);
}