package com.prueba.sena.repository;

import com.prueba.sena.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    
    // FUNCIONALIDAD PRINCIPAL: Buscar órdenes de un paciente por historia_id
    List<Order> findByHistoriaId(Integer historiaId);
    
    // Buscar órdenes de múltiples historias (si un paciente tiene varias)
    List<Order> findByHistoriaIdIn(List<Integer> historiaIds);
    
    // Buscar por legacy_id específico
    Order findByLegacyId(Integer legacyId);
    
    // Filtrar por fecha (útil para mostrar órdenes recientes primero)
    List<Order> findByHistoriaIdOrderByFechaDesc(Integer historiaId);
    
    // Buscar órdenes en un rango de fechas
    List<Order> findByHistoriaIdAndFechaBetween(Integer historiaId, LocalDateTime startDate, LocalDateTime endDate);
    
    // ===== NUEVAS FUNCIONALIDADES DE FILTRADO =====
    
    // Buscar órdenes por múltiples historias y rango de fechas
    List<Order> findByHistoriaIdInAndFechaBetween(List<Integer> historiaIds, LocalDateTime startDate, LocalDateTime endDate);
    
    // Buscar órdenes por número de orden (legacy_id como String para búsqueda parcial)
    List<Order> findByHistoriaIdInAndLegacyIdContaining(List<Integer> historiaIds, String orderNumber);
    
    // Buscar órdenes por historia_ids, rango de fechas y número de orden
    List<Order> findByHistoriaIdInAndFechaBetweenAndLegacyIdContaining(
        List<Integer> historiaIds, 
        LocalDateTime startDate, 
        LocalDateTime endDate, 
        String orderNumber
    );
    
    // Buscar órdenes solo por múltiples historias ordenadas por fecha descendente
    List<Order> findByHistoriaIdInOrderByFechaDesc(List<Integer> historiaIds);
}