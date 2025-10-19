package com.prueba.sena.service;

import com.prueba.sena.model.*;
import com.prueba.sena.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class PatientPortalService {
    
    @Autowired private PersonRepository personRepository;
    @Autowired private TarjeteroRepository tarjeteroRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private TestRepository testRepository;
    @Autowired private ProfessionalRepository professionalRepository;
    @Autowired private GroupRepository groupRepository;
    
    /**
     * FUNCIONALIDAD PRINCIPAL: Buscar paciente por documento
     */
    public Person findPatientByDocument(String numeroDocumento) {
        Person person = personRepository.findByNumeroId(numeroDocumento);
        if (person == null) {
            throw new RuntimeException("Paciente no encontrado con documento: " + numeroDocumento);
        }
        return person;
    }
    
    /**
     * FUNCIONALIDAD PRINCIPAL: Obtener todas las órdenes de un paciente
     */
    public List<Order> getPatientOrders(String numeroDocumento) {
        // 1. Buscar paciente
        Person patient = findPatientByDocument(numeroDocumento);
        
        // 2. Buscar todas las historias del paciente
        List<Tarjetero> tarjeteros = tarjeteroRepository.findByPersonLegacyId(patient.getLegacyId());
        
        if (tarjeteros.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 3. Obtener los IDs de las historias
        List<Integer> historiaIds = tarjeteros.stream()
            .map(Tarjetero::getLegacyId)
            .collect(Collectors.toList());
        
        // 4. Buscar órdenes por historia IDs (ordenadas por fecha descendente)
        List<Order> orders = orderRepository.findByHistoriaIdIn(historiaIds);
        
        // 5. Ordenar por fecha más reciente primero
        orders.sort((o1, o2) -> o2.getFecha().compareTo(o1.getFecha()));
        
        return orders;
    }
    
    /**
     * FUNCIONALIDAD PRINCIPAL: Obtener orden completa con resultados
     */
    public Map<String, Object> getOrderWithResults(Integer orderLegacyId) {
        // 1. Buscar la orden
        Order order = orderRepository.findByLegacyId(orderLegacyId);
        if (order == null) {
            throw new RuntimeException("Orden no encontrada: " + orderLegacyId);
        }
        
        // 2. Buscar información del profesional
        Professional professional = professionalRepository.findByLegacyId(order.getProfesionalOrdenaId());
        
        // 3. Buscar información de los tests
        List<Integer> testIds = order.getResults().stream()
            .map(OrderResult::getIdPrueba)
            .collect(Collectors.toList());
        
        List<Test> tests = testRepository.findByLegacyIdIn(testIds);
        
        // 4. Crear mapa test_id -> test para fácil acceso
        Map<Integer, Test> testMap = tests.stream()
            .collect(Collectors.toMap(Test::getLegacyId, test -> test));
        
        // 5. Enriquecer results con información de los tests
        List<Map<String, Object>> enrichedResults = order.getResults().stream()
            .map(result -> {
                Map<String, Object> resultInfo = new HashMap<>();
                resultInfo.put("result", result);
                
                Test test = testMap.get(result.getIdPrueba());
                resultInfo.put("test", test);
                
                return resultInfo;
            })
            .collect(Collectors.toList());
        
        // 6. Ensamblar respuesta completa
        Map<String, Object> response = new HashMap<>();
        response.put("order", order);
        response.put("professional", professional);
        response.put("results", enrichedResults);
        
        return response;
    }
    
    /**
     * Método de conveniencia: Información completa del paciente
     */
    public Map<String, Object> getPatientCompleteInfo(String numeroDocumento) {
        Person patient = findPatientByDocument(numeroDocumento);
        List<Order> orders = getPatientOrders(numeroDocumento);
        
        Map<String, Object> patientInfo = new HashMap<>();
        patientInfo.put("patient", patient);
        patientInfo.put("totalOrders", orders.size());
        patientInfo.put("orders", orders);
        
        return patientInfo;
    }
    
    // ===== NUEVAS FUNCIONALIDADES =====
    
    /**
     * NUEVO: Obtener perfil completo del paciente con toda su información
     */
    public Map<String, Object> getPatientProfile(String numeroDocumento) {
        Person patient = findPatientByDocument(numeroDocumento);
        
        Map<String, Object> profile = new HashMap<>();
        profile.put("tipoIdentificacion", patient.getTipoIdentificacionId());
        profile.put("numeroIdentificacion", patient.getNumeroId());
        profile.put("nombreCompleto", buildFullName(patient));
        profile.put("fechaNacimiento", patient.getFechaNac());
        profile.put("sexoBiologico", patient.getSexoId());
        profile.put("direccionResidencia", patient.getDireccion());
        profile.put("numeroCelular", patient.getTelMovil());
        profile.put("correoElectronico", patient.getEmail());
        
        return profile;
    }
    
    /**
     * NUEVO: Buscar órdenes con filtros avanzados
     */
    public List<Order> searchPatientOrders(String numeroDocumento, String orderNumber, 
                                         LocalDateTime startDate, LocalDateTime endDate) {
        // 1. Obtener historias del paciente
        Person patient = findPatientByDocument(numeroDocumento);
        List<Tarjetero> tarjeteros = tarjeteroRepository.findByPersonLegacyId(patient.getLegacyId());
        
        if (tarjeteros.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Integer> historiaIds = tarjeteros.stream()
            .map(Tarjetero::getLegacyId)
            .collect(Collectors.toList());
        
        // 2. Aplicar filtros
        List<Order> orders;
        
        if (orderNumber != null && startDate != null && endDate != null) {
            // Filtro completo: número + fechas
            orders = orderRepository.findByHistoriaIdInAndFechaBetweenAndLegacyIdContaining(
                historiaIds, startDate, endDate, orderNumber);
        } else if (orderNumber != null) {
            // Solo filtro por número
            orders = orderRepository.findByHistoriaIdInAndLegacyIdContaining(historiaIds, orderNumber);
        } else if (startDate != null && endDate != null) {
            // Solo filtro por fechas
            orders = orderRepository.findByHistoriaIdInAndFechaBetween(historiaIds, startDate, endDate);
        } else {
            // Sin filtros, traer todas ordenadas por fecha
            orders = orderRepository.findByHistoriaIdInOrderByFechaDesc(historiaIds);
        }
        
        return orders;
    }
    
    /**
     * NUEVO: Obtener resultados agrupados por categoría
     */
    public Map<String, Object> getOrderResultsGrouped(Integer orderLegacyId) {
        // 1. Obtener la orden
        Order order = orderRepository.findByLegacyId(orderLegacyId);
        if (order == null) {
            throw new RuntimeException("Orden no encontrada: " + orderLegacyId);
        }
        
        // 2. Obtener professional
        Professional professional = professionalRepository.findByLegacyId(order.getProfesionalOrdenaId());
        
        // 3. Obtener tests con información de grupo
        List<Integer> testIds = order.getResults().stream()
            .map(OrderResult::getIdPrueba)
            .collect(Collectors.toList());
        
        List<Test> tests = testRepository.findByLegacyIdInOrderByGrupoIdAsc(testIds);
        
        // 4. Obtener información de los grupos
        List<Integer> groupIds = tests.stream()
            .map(Test::getGrupoId)
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());
        
        List<Group> groups = groupRepository.findByLegacyIdIn(groupIds);
        Map<Integer, Group> groupMap = groups.stream()
            .collect(Collectors.toMap(Group::getLegacyId, group -> group));
        
        // 5. Crear mapa test_id -> test
        Map<Integer, Test> testMap = tests.stream()
            .collect(Collectors.toMap(Test::getLegacyId, test -> test));
        
        // 6. Agrupar resultados por grupo
        Map<String, List<Map<String, Object>>> groupedResults = new LinkedHashMap<>();
        
        for (OrderResult result : order.getResults()) {
            Test test = testMap.get(result.getIdPrueba());
            if (test == null) continue;
            
            Group group = groupMap.get(test.getGrupoId());
            String groupName = (group != null) ? group.getNombre() : "Sin Grupo";
            
            if (!groupedResults.containsKey(groupName)) {
                groupedResults.put(groupName, new ArrayList<>());
            }
            
            Map<String, Object> resultInfo = new HashMap<>();
            resultInfo.put("test", test);
            resultInfo.put("result", result);
            
            groupedResults.get(groupName).add(resultInfo);
        }
        
        // 7. Ensamblar respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("order", order);
        response.put("professional", professional);
        response.put("groupedResults", groupedResults);
        
        return response;
    }
    
    /**
     * Método auxiliar: Construir nombre completo
     */
    private String buildFullName(Person person) {
        StringBuilder fullName = new StringBuilder();
        
        if (person.getNombres() != null) {
            if (person.getNombres().getNombre1() != null) {
                fullName.append(person.getNombres().getNombre1()).append(" ");
            }
            if (person.getNombres().getNombre2() != null) {
                fullName.append(person.getNombres().getNombre2()).append(" ");
            }
        }
        
        if (person.getApellidos() != null) {
            if (person.getApellidos().getApellido1() != null) {
                fullName.append(person.getApellidos().getApellido1()).append(" ");
            }
            if (person.getApellidos().getApellido2() != null) {
                fullName.append(person.getApellidos().getApellido2());
            }
        }
        
        return fullName.toString().trim();
    }
}