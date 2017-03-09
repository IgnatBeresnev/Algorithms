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
            if (v.dist == Integer.MAX_VALUE) continue; // int overflow
            for (Edge edge : v.edges) {
                relax(v, edge.vertex, edge.weight);
            }
        }
    }

    // u - min vertex from the queue, v - edge(u, v), weight = w(u, v)
    private static void relax(Vertex u, Vertex v, int weight) {
        if (v.dist > (u.dist + weight)) {
            v.dist = u.dist + weight;
            v.parent = u;
        }
    }
}
