package com.microcookie.api.service;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.*;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ApiTrafficSimulatorService {

    // Store active simulations
    private final Map<String, ScheduledFuture<?>> activeSimulations = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    // Store API call data
    private final Map<String, Map<String, AtomicLong>> apiProductCallCounts = new ConcurrentHashMap<>();
    private final List<ApiCallEvent> recentCalls = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong totalCalls = new AtomicLong(0);

    // Available options for simulation
    private final List<String> availableApis = Arrays.asList("Pricing API", "Customer API", "Payments API", "Delivery API");
    private final List<String> availableProducts = Arrays.asList("EV1", "EV2", "EV3");

    /**
     * Simulate a single API call
     */
    public Map<String, Object> simulateApiCall(String apiName, String productId, int volume) {
        validateInput(apiName, productId);

        // Record the API call
        ApiCallEvent event = recordApiCall(apiName, productId, volume);

        // Update call counts
        apiProductCallCounts
            .computeIfAbsent(apiName, k -> new ConcurrentHashMap<>())
            .computeIfAbsent(productId, k -> new AtomicLong(0))
            .addAndGet(volume);

        totalCalls.addAndGet(volume);

        return Map.of(
            "success", true,
            "event", event,
            "totalCalls", totalCalls.get(),
            "apiCallCounts", getApiCallCounts(apiName),
            "timestamp", LocalDateTime.now()
        );
    }

    /**
     * Start continuous simulation with random or configured traffic
     */
    public String startContinuousSimulation(int durationSeconds, int frequencyPerSecond, boolean randomTraffic) {
        String simulationId = "sim_" + System.currentTimeMillis();
        
        ScheduledFuture<?> simulation = scheduler.scheduleAtFixedRate(() -> {
            try {
                if (randomTraffic) {
                    generateRandomTraffic();
                } else {
                    generatePatternedTraffic();
                }
            } catch (Exception e) {
                System.err.println("Error in simulation: " + e.getMessage());
            }
        }, 0, 1000 / frequencyPerSecond, TimeUnit.MILLISECONDS);

        activeSimulations.put(simulationId, simulation);

        // Auto-stop after duration
        scheduler.schedule(() -> {
            stopSimulation(simulationId);
        }, durationSeconds, TimeUnit.SECONDS);

        return simulationId;
    }

    /**
     * Stop a specific simulation
     */
    public boolean stopSimulation(String simulationId) {
        ScheduledFuture<?> simulation = activeSimulations.remove(simulationId);
        if (simulation != null) {
            simulation.cancel(false);
            return true;
        }
        return false;
    }

    /**
     * Get current API relationships based on call patterns
     */
    public Map<String, Object> getCurrentRelationships() {
        Map<String, Object> result = new HashMap<>();
        
        // Create nodes for D3.js visualization
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> links = new ArrayList<>();

        Set<String> allEntities = new HashSet<>();
        allEntities.addAll(availableApis);
        allEntities.addAll(availableProducts);

        // Add nodes
        for (String api : availableApis) {
            nodes.add(Map.of(
                "id", api,
                "type", "api",
                "group", 1,
                "callCount", getTotalCallsForApi(api)
            ));
        }

        for (String product : availableProducts) {
            nodes.add(Map.of(
                "id", product,
                "type", "product", 
                "group", 2,
                "callCount", getTotalCallsForProduct(product)
            ));
        }

        // Add links based on call relationships
        for (Map.Entry<String, Map<String, AtomicLong>> apiEntry : apiProductCallCounts.entrySet()) {
            String apiName = apiEntry.getKey();
            for (Map.Entry<String, AtomicLong> productEntry : apiEntry.getValue().entrySet()) {
                String productId = productEntry.getKey();
                long callCount = productEntry.getValue().get();
                
                if (callCount > 0) {
                    links.add(Map.of(
                        "source", apiName,
                        "target", productId,
                        "value", callCount,
                        "weight", Math.min(callCount / 10.0, 10.0) // Normalize for visualization
                    ));
                }
            }
        }

        result.put("nodes", nodes);
        result.put("links", links);
        result.put("totalRelationships", links.size());
        result.put("lastUpdated", LocalDateTime.now());

        return result;
    }

    /**
     * Get traffic statistics
     */
    public Map<String, Object> getTrafficStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalCalls", totalCalls.get());
        stats.put("activeSimulations", activeSimulations.size());
        stats.put("recentCallsCount", recentCalls.size());
        stats.put("apiBreakdown", getApiBreakdown());
        stats.put("productBreakdown", getProductBreakdown());
        stats.put("recentActivity", getRecentActivity(10));
        
        return stats;
    }

    /**
     * Reset all simulation data
     */
    public void resetAllData() {
        // Stop all active simulations
        activeSimulations.values().forEach(sim -> sim.cancel(false));
        activeSimulations.clear();
        
        // Clear data
        apiProductCallCounts.clear();
        recentCalls.clear();
        totalCalls.set(0);
    }

    /**
     * Get available APIs and Products
     */
    public Map<String, Object> getAvailableOptions() {
        return Map.of(
            "apis", availableApis,
            "products", availableProducts
        );
    }

    // Private helper methods

    private void validateInput(String apiName, String productId) {
        if (apiName == null || apiName.trim().isEmpty()) {
            throw new IllegalArgumentException("API name cannot be empty");
        }
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be empty");
        }
    }

    private ApiCallEvent recordApiCall(String apiName, String productId, int volume) {
        ApiCallEvent event = new ApiCallEvent(apiName, productId, volume, LocalDateTime.now());
        recentCalls.add(event);
        
        // Keep only recent calls (last 1000)
        if (recentCalls.size() > 1000) {
            recentCalls.remove(0);
        }
        
        return event;
    }

    private void generateRandomTraffic() {
        Random random = new Random();
        String randomApi = availableApis.get(random.nextInt(availableApis.size()));
        String randomProduct = availableProducts.get(random.nextInt(availableProducts.size()));
        int volume = random.nextInt(5) + 1; // 1-5 calls
        
        simulateApiCall(randomApi, randomProduct, volume);
    }

    private void generatePatternedTraffic() {
        // Generate more realistic patterns - e.g., Pricing API used more by EV1
        Random random = new Random();
        
        if (random.nextDouble() < 0.4) { // 40% chance - Pricing API with EV1
            simulateApiCall("Pricing API", "EV1", random.nextInt(3) + 1);
        } else if (random.nextDouble() < 0.7) { // 30% chance - Customer API distributed
            String product = availableProducts.get(random.nextInt(availableProducts.size()));
            simulateApiCall("Customer API", product, random.nextInt(2) + 1);
        } else { // 30% chance - Other APIs
            String api = availableApis.get(random.nextInt(availableApis.size()));
            String product = availableProducts.get(random.nextInt(availableProducts.size()));
            simulateApiCall(api, product, 1);
        }
    }

    private Map<String, Long> getApiCallCounts(String apiName) {
        Map<String, Long> counts = new HashMap<>();
        Map<String, AtomicLong> apiCounts = apiProductCallCounts.get(apiName);
        if (apiCounts != null) {
            apiCounts.forEach((product, count) -> counts.put(product, count.get()));
        }
        return counts;
    }

    private long getTotalCallsForApi(String apiName) {
        return apiProductCallCounts.getOrDefault(apiName, new HashMap<>())
            .values().stream()
            .mapToLong(AtomicLong::get)
            .sum();
    }

    private long getTotalCallsForProduct(String productId) {
        return apiProductCallCounts.values().stream()
            .mapToLong(productMap -> productMap.getOrDefault(productId, new AtomicLong(0)).get())
            .sum();
    }

    private Map<String, Long> getApiBreakdown() {
        Map<String, Long> breakdown = new HashMap<>();
        for (String api : availableApis) {
            breakdown.put(api, getTotalCallsForApi(api));
        }
        return breakdown;
    }

    private Map<String, Long> getProductBreakdown() {
        Map<String, Long> breakdown = new HashMap<>();
        for (String product : availableProducts) {
            breakdown.put(product, getTotalCallsForProduct(product));
        }
        return breakdown;
    }

    private List<ApiCallEvent> getRecentActivity(int limit) {
        int size = recentCalls.size();
        int start = Math.max(0, size - limit);
        return new ArrayList<>(recentCalls.subList(start, size));
    }

    // Inner class for API call events
    public static class ApiCallEvent {
        private final String apiName;
        private final String productId;
        private final int volume;
        private final LocalDateTime timestamp;

        public ApiCallEvent(String apiName, String productId, int volume, LocalDateTime timestamp) {
            this.apiName = apiName;
            this.productId = productId;
            this.volume = volume;
            this.timestamp = timestamp;
        }

        // Getters
        public String getApiName() { return apiName; }
        public String getProductId() { return productId; }
        public int getVolume() { return volume; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
}