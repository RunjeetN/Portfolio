package ngordnet.main;

import java.util.*;

public class Graph {
    Map<String, List<String>> g;

    public Graph() {
        g = new HashMap<>();
    }

    public void addEdge(String parentId, String childId) {
        if (!g.containsKey(parentId)) {
            g.put(parentId, new ArrayList<String>());
        }
        if (!g.containsKey(childId)) {
            g.put(childId, new ArrayList<>());
        }
        g.get(parentId).add(childId);
    }

    public List<String> getNeighbors(String id) {
        return g.get(id);
    }

    public String toString() {
        return g.toString();
    }
}
