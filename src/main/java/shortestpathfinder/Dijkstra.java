package shortestpathfinder;

import java.util.*;

public class Dijkstra {

    // Method to calculate the shortest distance and the path between two locations
    public static String calculateShortestPath(String start, String end) {
        // Graph representation with cities and distances (location -> {neighbor -> distance})
        Map<String, Map<String, Integer>> graph = new HashMap<>();

        // Example cities and distances
        graph.put("Dhanmondi", Map.of("Gulshan", 8, "Uttara", 15, "Mirpur", 6));
        graph.put("Gulshan", Map.of("Dhanmondi", 8, "Uttara", 12, "Mirpur", 9, "Narayanganj", 20));
        graph.put("Uttara", Map.of("Dhanmondi", 15, "Gulshan", 12, "Mirpur", 13, "Narayanganj", 22));
        graph.put("Mirpur", Map.of("Dhanmondi", 6, "Gulshan", 9, "Uttara", 13, "Tangail", 60));
        graph.put("Narayanganj", Map.of("Gulshan", 20, "Uttara", 22, "Tangail", 65));
        graph.put("Tangail", Map.of("Mirpur", 60, "Narayanganj", 65));

        // Dijkstra's Algorithm
        Set<String> visited = new HashSet<>();
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();  // To store the previous node for each location
        for (String location : graph.keySet()) {
            distances.put(location, Integer.MAX_VALUE); // Set all distances to infinity
            previous.put(location, null); // Initially, no previous node
        }
        distances.put(start, 0); // Distance to start is 0

        // Priority Queue (min-heap)
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        pq.add(start);

        while (!pq.isEmpty()) {
            String currentLocation = pq.poll();
            visited.add(currentLocation);

            // Get neighbors and calculate distances
            for (Map.Entry<String, Integer> neighbor : graph.get(currentLocation).entrySet()) {
                String neighborLocation = neighbor.getKey();
                int newDist = distances.get(currentLocation) + neighbor.getValue();
                if (newDist < distances.get(neighborLocation) && !visited.contains(neighborLocation)) {
                    distances.put(neighborLocation, newDist);
                    previous.put(neighborLocation, currentLocation); // Set the previous node
                    pq.add(neighborLocation);
                }
            }
        }

        // Reconstruct the path from end to start
        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);  // The path was constructed backwards, so reverse it

        // If there's no valid path, return a message
        if (distances.get(end) == Integer.MAX_VALUE) {
            return "No path found.";
        }

        // Return the path and the distance
        return "Shortest Distance: " + distances.get(end) + " km\nRoute: " + String.join(" -> ", path);
    }
}
