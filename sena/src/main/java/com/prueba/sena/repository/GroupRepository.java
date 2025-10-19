package com.prueba.sena.repository;

import com.prueba.sena.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
    
    // Buscar grupo por legacy_id
    Group findByLegacyId(Integer legacyId);
    
    // Buscar múltiples grupos por legacy_ids
    List<Group> findByLegacyIdIn(List<Integer> legacyIds);
    
    // Buscar grupos activos ordenados por orden
    List<Group> findByActivoTrueOrderByOrdenAsc();
    
    // Buscar grupos por nombre (búsqueda parcial)
    List<Group> findByNombreContainingIgnoreCase(String nombre);
}