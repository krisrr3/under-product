package com.microcookie.api.controller;

import com.microcookie.api.service.ApiTrafficSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/traffic-simulator")
@CrossOrigin(origins = "*")
public class ApiTrafficSimulatorController {

    @Autowired
    private ApiTrafficSimulatorService simulatorService;

    /**
     * Simulate a single API call
     */
    @PostMapping("/simulate-call")
    public ResponseEntity<Map<String, Object>> simulateApiCall(@RequestBody Map<String, Object> request) {
        try {
            String apiName = (String) request.get("apiName");
            String productId = (String) request.get("productId");
            Integer volume = request.get("volume") != null ? (Integer) request.get("volume") : 1;
            
            Map<String, Object> result = simulatorService.simulateApiCall(apiName, productId, volume);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Failed to simulate API call: " + e.getMessage()));
        }
    }

    /**
     * Start continuous traffic simulation
     */
    @PostMapping("/start-simulation")
    public ResponseEntity<Map<String, Object>> startSimulation(@RequestBody Map<String, Object> config) {
        try {
            Integer duration = config.get("duration") != null ? (Integer) config.get("duration") : 30;
            Integer frequency = config.get("frequency") != null ? (Integer) config.get("frequency") : 2;
            Boolean randomTraffic = config.get("randomTraffic") != null ? (Boolean) config.get("randomTraffic") : true;
            
            String simulationId = simulatorService.startContinuousSimulation(duration, frequency, randomTraffic);
            return ResponseEntity.ok(Map.of(
                "simulationId", simulationId,
                "status", "started",
                "duration", duration,
                "frequency", frequency
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Failed to start simulation: " + e.getMessage()));
        }
    }

    /**
     * Stop active simulation
     */
    @PostMapping("/stop-simulation/{simulationId}")
    public ResponseEntity<Map<String, Object>> stopSimulation(@PathVariable String simulationId) {
        try {
            boolean stopped = simulatorService.stopSimulation(simulationId);
            return ResponseEntity.ok(Map.of(
                "simulationId", simulationId,
                "status", stopped ? "stopped" : "not_found"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Failed to stop simulation: " + e.getMessage()));
        }
    }

    /**
     * Get current API relationships and traffic data
     */
    @GetMapping("/relationships")
    public ResponseEntity<Map<String, Object>> getCurrentRelationships() {
        try {
            Map<String, Object> relationships = simulatorService.getCurrentRelationships();
            return ResponseEntity.ok(relationships);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Failed to get relationships: " + e.getMessage()));
        }
    }

    /**
     * Get real-time traffic statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getTrafficStats() {
        try {
            Map<String, Object> stats = simulatorService.getTrafficStatistics();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Failed to get stats: " + e.getMessage()));
        }
    }

    /**
     * Reset all simulation data
     */
    @PostMapping("/reset")
    public ResponseEntity<Map<String, Object>> resetSimulation() {
        try {
            simulatorService.resetAllData();
            return ResponseEntity.ok(Map.of("status", "reset_complete"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Failed to reset: " + e.getMessage()));
        }
    }

    /**
     * Get available APIs and Products for simulation
     */
    @GetMapping("/available-options")
    public ResponseEntity<Map<String, Object>> getAvailableOptions() {
        try {
            Map<String, Object> options = simulatorService.getAvailableOptions();
            return ResponseEntity.ok(options);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Failed to get options: " + e.getMessage()));
        }
    }
}