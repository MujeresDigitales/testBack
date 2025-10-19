package com.prueba.sena.repository;

import com.prueba.sena.model.Test;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TestRepository extends MongoRepository<Test, String> {
    
    // Buscar tests por sus legacy_ids (para obtener nombres de las pruebas)
    List<Test> findByLegacyIdIn(List<Integer> testLegacyIds);
    
    // Buscar test específico
    Test findByLegacyId(Integer legacyId);
    
    // Buscar por código de prueba
    Test findByCodigoPrueba(String codigoPrueba);
    
    // ===== FUNCIONALIDADES DE AGRUPACIÓN =====
    
    // Buscar tests por grupo_id
    List<Test> findByGrupoId(Integer grupoId);
    
    // Buscar tests por múltiples grupo_ids
    List<Test> findByGrupoIdIn(List<Integer> grupoIds);
    
    // Buscar tests por legacy_ids y agruparlos por grupo_id (necesario para organizar resultados)
    List<Test> findByLegacyIdInOrderByGrupoIdAsc(List<Integer> legacyIds);
}