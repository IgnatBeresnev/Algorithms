package me.beresnev.algorithms.graphs;

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

    private static int INFINITY = Integer.MAX_VALUE;

    /**
     * Bellman-Ford algorithm solves the problem of negative edges. However,
     * negative cycles are still a problem - we can't compute them properly.
     * However, if there's a negative cycle, the algorithm will let us know
     * by returning false. If there are none, then returns true.
     * <p>
     * Pretty straight-forward algorithm. Relax all vertices N-1 times (at most).
     * Then go through all of them once again and check for negative cycles.
     * Time complexity: O(V*E)? V iterations through E edges.
     */
    private BellmanFord() {
    }

    public static boolean bellmanFord(WeightedGraph g, Vertex source) {
        List<Vertex> vertices = g.getVertices();
        for (int i = 0; i < vertices.size() - 1; i++) {
            // small possible optimization: if nothing happens during
            // an iteration of this cycle, then break sooner, because bellmanFord
            // requires N-1 iterations at most, but it is not the minimum
            for (Vertex v : vertices) {
                if (v.dist == INFINITY) continue; // int overflow
                for (Edge e : v.edges)
                    relax(v, e.vertex, e.weight);
            }
        }

        //checking for negative cycles
        for (Vertex v : vertices) {
            for (Edge e : v.edges) {
                if (e.vertex.dist == INFINITY || v.dist == INFINITY)
                    return false;
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
