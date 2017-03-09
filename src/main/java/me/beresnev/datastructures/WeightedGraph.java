package me.beresnev.datastructures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 09.03.17.
 */
public class WeightedGraph {
    private List<Vertex> vertices = new ArrayList<>();

    public WeightedGraph() {
    }

    public void addVertex(Vertex a) {
        vertices.add(a);
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public static class Vertex implements Comparable<Vertex> {
        private final String label;
        public int dist = Integer.MAX_VALUE; // = infinity
        public Set<Edge> edges = new HashSet<>();
        public Vertex parent; // for shortest path

        public Vertex(String label) {
            this.label = label;
        }

        public void addEdge(Vertex to, int weight) {
            edges.add(new Edge(to, weight));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vertex vertex = (Vertex) o;

            if (dist != vertex.dist) return false;
            if (label != null ? !label.equals(vertex.label) : vertex.label != null) return false;
            return parent != null ? parent.equals(vertex.parent) : vertex.parent == null;
        }

        @Override
        public int hashCode() {
            int result = label != null ? label.hashCode() : 0;
            result = 31 * result + dist;
            result = 31 * result + (parent != null ? parent.hashCode() : 0);
            return result;
        }

        @Override
        public int compareTo(Vertex o) {
            return (dist == o.dist) ? 0 : (dist < o.dist) ? -1 : 1;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public static class Edge {
        public Vertex vertex;
        public int weight;

        private Edge(Vertex vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            if (weight != edge.weight) return false;
            return vertex != null ? vertex.equals(edge.vertex) : edge.vertex == null;
        }

        @Override
        public int hashCode() {
            int result = vertex != null ? vertex.hashCode() : 0;
            result = 31 * result + weight;
            return result;
        }

        @Override
        public String toString() {
            return "(" + vertex + ", " + weight + ")";
        }
    }
}
