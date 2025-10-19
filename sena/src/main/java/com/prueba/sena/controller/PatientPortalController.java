package com.prueba.sena.controller;

import com.prueba.sena.model.*;
import com.prueba.sena.service.PatientPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/patient-portal")
@CrossOrigin(origins = "*")
public class PatientPortalController {
    
    @Autowired
    private PatientPortalService patientPortalService;
    
    /**
     * ENDPOINT PRINCIPAL: Buscar paciente por número de documento
     * GET /api/patient-portal/patient/{numeroDocumento}
     */
    @GetMapping("/patient/{numeroDocumento}")
    public ResponseEntity<?> getPatient(@PathVariable String numeroDocumento) {
        try {
            Person patient = patientPortalService.findPatientByDocument(numeroDocumento);
            return ResponseEntity.ok(patient);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", true);
            error.put("message", e.getMessage());
            error.put("numeroDocumento", numeroDocumento);
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * ENDPOINT PRINCIPAL: Obtener todas las órdenes de un paciente
     * GET /api/patient-portal/patient/{numeroDocumento}/orders
     */
    @GetMapping("/patient/{numeroDocumento}/orders")
    public ResponseEntity<?> getPatientOrders(@PathVariable String numeroDocumento) {
        try {
            List<Order> orders = patientPortalService.getPatientOrders(numeroDocumento);
            
            Map<String, Object> response = new HashMap<>();
            response.put("numeroDocumento", numeroDocumento);
            response.put("totalOrders", orders.size());
            response.put("orders", orders);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", true);
            error.put("message", e.getMessage());
            error.put("numeroDocumento", numeroDocumento);
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * ENDPOINT PRINCIPAL: Obtener orden específica con resultados completos
     * GET /api/patient-portal/order/{orderLegacyId}/results
     */
    @GetMapping("/order/{orderLegacyId}/results")
    public ResponseEntity<?> getOrderResults(@PathVariable Integer orderLegacyId) {
        try {
            Map<String, Object> orderWithResults = patientPortalService.getOrderWithResults(orderLegacyId);
            return ResponseEntity.ok(orderWithResults);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", true);
            error.put("message", e.getMessage());
            error.put("orderLegacyId", orderLegacyId);
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * ENDPOINT DE CONVENIENCIA: Información completa del paciente
     * GET /api/patient-portal/patient/{numeroDocumento}/complete
     */
    @GetMapping("/patient/{numeroDocumento}/complete")
    public ResponseEntity<?> getPatientCompleteInfo(@PathVariable String numeroDocumento) {
        try {
            Map<String, Object> completeInfo = patientPortalService.getPatientCompleteInfo(numeroDocumento);
            return ResponseEntity.ok(completeInfo);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", true);
            error.put("message", e.getMessage());
            error.put("numeroDocumento", numeroDocumento);
            return ResponseEntity.notFound().build();
        }
    }
    
    // ===== NUEVOS ENDPOINTS =====
    
    /**
     * NUEVO: Obtener perfil completo del paciente
     * GET /api/patient-portal/patient/{numeroDocumento}/profile
     */
    @GetMapping("/patient/{numeroDocumento}/profile")
    public ResponseEntity<?> getPatientProfile(@PathVariable String numeroDocumento) {
        try {
            Map<String, Object> profile = patientPortalService.getPatientProfile(numeroDocumento);
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", true);
            error.put("message", e.getMessage());
            error.put("numeroDocumento", numeroDocumento);
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * NUEVO: Buscar órdenes con filtros avanzados
     * GET /api/patient-portal/patient/{numeroDocumento}/orders/search
     * Parámetros opcionales: orderNumber, startDate, endDate
     */
    @GetMapping("/patient/{numeroDocumento}/orders/search")
    public ResponseEntity<?> searchPatientOrders(
            @PathVariable String numeroDocumento,
            @RequestParam(required = false) String orderNumber,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        try {
            LocalDateTime startDateTime = null;
            LocalDateTime endDateTime = null;
            
            // Convertir fechas si se proporcionan
            if (startDate != null && !startDate.isEmpty()) {
                startDateTime = LocalDateTime.parse(startDate + "T00:00:00");
            }
            if (endDate != null && !endDate.isEmpty()) {
                endDateTime = LocalDateTime.parse(endDate + "T23:59:59");
            }
            
            List<Order> orders = patientPortalService.searchPatientOrders(
                numeroDocumento, orderNumber, startDateTime, endDateTime);
            
            Map<String, Object> response = new HashMap<>();
            response.put("numeroDocumento", numeroDocumento);
            response.put("filters", Map.of(
                "orderNumber", orderNumber != null ? orderNumber : "N/A",
                "startDate", startDate != null ? startDate : "N/A", 
                "endDate", endDate != null ? endDate : "N/A"
            ));
            response.put("totalOrders", orders.size());
            response.put("orders", orders);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", true);
            error.put("message", e.getMessage());
            error.put("numeroDocumento", numeroDocumento);
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * NUEVO: Obtener resultados agrupados por categoría
     * GET /api/patient-portal/order/{orderLegacyId}/results/grouped
     */
    @GetMapping("/order/{orderLegacyId}/results/grouped")
    public ResponseEntity<?> getOrderResultsGrouped(@PathVariable Integer orderLegacyId) {
        try {
            Map<String, Object> groupedResults = patientPortalService.getOrderResultsGrouped(orderLegacyId);
            return ResponseEntity.ok(groupedResults);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", true);
            error.put("message", e.getMessage());
            error.put("orderLegacyId", orderLegacyId);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * ENDPOINT DE PRUEBA: Verificar conectividad del servicio
     * GET /api/patient-portal/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "Patient Portal API - Extended Version");
        health.put("timestamp", new Date());
        health.put("endpoints", Arrays.asList(
            "GET /api/patient-portal/patient/{numeroDocumento}",
            "GET /api/patient-portal/patient/{numeroDocumento}/profile",
            "GET /api/patient-portal/patient/{numeroDocumento}/orders", 
            "GET /api/patient-portal/patient/{numeroDocumento}/orders/search",
            "GET /api/patient-portal/order/{orderLegacyId}/results",
            "GET /api/patient-portal/order/{orderLegacyId}/results/grouped",
            "GET /api/patient-portal/patient/{numeroDocumento}/complete"
        ));
        return ResponseEntity.ok(health);
    }
}