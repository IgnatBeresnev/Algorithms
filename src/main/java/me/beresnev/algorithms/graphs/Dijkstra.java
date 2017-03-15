package me.beresnev.algorithms.graphs;

import me.beresnev.datastructures.WeightedGraph;
import me.beresnev.datastructures.WeightedGraph.Edge;
import me.beresnev.datastructures.WeightedGraph.Vertex;

import java.util.PriorityQueue;
import java.util.Queue;


/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 09.03.17.
 */
public class Dijkstra {

    /**
     * !!! DISCLAIMER !!!
     * As of right now, this algorithm isn't functioning properly. Unfortunately,
     * PriorityQueue in Java doesn't restructure itself when you update values.
     * In order for it to work, change PriorityQueue to FibonacciHeap or MinHeap.
     * <p>
     * Greedy algorithm. We start with source, then update the distance
     * between source and all of its edges. Then select the closest edge
     * (being greedy), and do the same for all of its edges. Repeat.
     */
    private Dijkstra() {
    }

    /**
     * DOES NOT FUNCTION PROPERLY. CHANGE PRIORITY QUEUE TO MINHEAP
     * OR FIBONACCIHEAP. TODO: Correct this algorithm
     */
    public static void dijkstra(WeightedGraph graph, Vertex source) {
        Queue<Vertex> queue = new PriorityQueue<>();

        source.dist = 0;
        queue.addAll(graph.getVertices());

        while (!queue.isEmpty()) {
            Vertex v = queue.poll();
            if (v.dist == Integer.MAX_VALUE) continue;
            for (Edge edge : v.edges) {
                relax(v, edge.vertex, edge.weight);
            }
        }
    }

    private static void relax(Vertex v, Vertex e, int weight) {
        if (e.dist > (v.dist + weight)) {
            e.dist = v.dist + weight;
            e.parent = v;
        }
    }
}
