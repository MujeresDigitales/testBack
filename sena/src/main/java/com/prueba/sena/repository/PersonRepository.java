package com.prueba.sena.repository;

import com.prueba.sena.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
    
    // Buscar paciente por número de documento (funcionalidad principal)
    Person findByNumeroId(String numeroId);
    
    // Buscar por legacy_id (para relaciones internas)
    Person findByLegacyId(Integer legacyId);
}