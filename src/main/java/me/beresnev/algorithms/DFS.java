package me.beresnev.algorithms;

import java.util.*;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 07.03.17.
 */
public class DFS {

    private DFS() {
    }

    static DFSResult DFS(Graph g) {
        DFSResult result = new DFSResult();
        for (Vertex v : g.vertices) {
            if (!result.parent.containsKey(v)) {
                DFSVisit(v, result, null);
            }
        }
        return result;
    }

    private static void DFSVisit(Vertex v, DFSResult result, Vertex parent) {
        result.parent.put(v, parent);
        result.time++;
        result.startTime.put(v, result.time);
        if (parent != null) {
            result.addEdgeType(parent, v, DFSResult.Type.TREE);
        }

        for (Vertex n : v.neighbours) {
            if (!result.parent.containsKey(n)) {
                DFSVisit(n, result, v);
            } else if (!result.finishTime.containsKey(n)) {
                result.addEdgeType(v, n, DFSResult.Type.BACK);
            } else if (result.startTime.get(v) < result.startTime.get(n)) {
                result.addEdgeType(v, n, DFSResult.Type.FORWARD);
            } else {
                result.addEdgeType(v, n, DFSResult.Type.CROSS);
            }
        }
        result.time++;
        result.finishTime.put(v, result.time);
        result.order.add(v);
    }

    static class DFSResult {
        int time = 0;
        Map<Vertex, Vertex> parent = new HashMap<>();
        Set<Vertex> order = new HashSet<>();
        Map<Vertex, Integer> startTime = new HashMap<>();
        Map<Vertex, Integer> finishTime = new HashMap<>();
        Map<String, Type> edgeType = new HashMap<>();

        private void addEdgeType(Vertex a, Vertex b, Type type) {
            if (a == null)
                a = new Vertex("Null");
            else if (b == null)
                b = new Vertex("Null");
            edgeType.put(a.label.toUpperCase() + b.label.toUpperCase(), type);
        }

        enum Type {
            TREE, BACK, FORWARD, CROSS
        }
    }

    static class Graph {
        List<Vertex> vertices = new LinkedList<>();
    }

    public static class Vertex {
        String label;
        List<Vertex> neighbours = new LinkedList<>();

        Vertex(String label) {
            this.label = label;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return label != null ? label.equals(vertex.label) : vertex.label == null;
        }

        @Override
        public int hashCode() {
            return label != null ? label.hashCode() * 31 : 0;
        }

        public String toString() {
            return label;
        }
    }
}
