package me.beresnev.algorithms;

import me.beresnev.datastructures.WeightedGraph;
import me.beresnev.datastructures.WeightedGraph.Edge;
import me.beresnev.datastructures.WeightedGraph.Vertex;

import java.util.PriorityQueue;
import java.util.Queue;


/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 09.03.17.
 */
public class Dijkstra {

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
