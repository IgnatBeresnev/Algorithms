package me.beresnev.algorithms;

import me.beresnev.datastructures.WeightedGraph;
import me.beresnev.datastructures.WeightedGraph.Edge;
import me.beresnev.datastructures.WeightedGraph.Vertex;

import java.util.List;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 11.03.17.
 */
public class BellmanFord {

    // not greedy, so no problem with negative edges. cycles are a problem
    private BellmanFord() {
    }

    public static boolean bellmanFord(WeightedGraph g, Vertex source) {
        List<Vertex> vertices = g.getVertices();
        for (int i = 0; i < vertices.size() - 1; i++) {
            // small possible optimization: if nothing happens during
            // an iteration of this cycle, then break sooner, because bellmanFord
            // requires (#vertices - 1) iterations at most, but it != minimum
            for (Vertex v : vertices) {
                if (v.dist == Integer.MAX_VALUE) continue; // int overflow
                for (Edge e : v.edges)
                    relax(v, e.vertex, e.weight);
            }
        }

        //checking for negative cycles, return false if it exists
        for (Vertex v : vertices) {
            for (Edge e : v.edges) {
                if (e.vertex.dist > (v.dist + e.weight))
                    return false;
            }
        }
        return true;
    }

    private static void relax(Vertex v, Vertex e, int weight) {
        if (e.dist > (v.dist + weight)) {
            e.dist = v.dist + weight;
            e.parent = v;
        }
    }
}
