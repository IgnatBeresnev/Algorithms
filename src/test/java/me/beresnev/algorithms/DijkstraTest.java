package me.beresnev.algorithms;

import me.beresnev.datastructures.WeightedGraph;
import me.beresnev.datastructures.WeightedGraph.Vertex;
import org.junit.Test;

import java.util.Stack;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 09.03.17.
 */
public class DijkstraTest {

    /**
     * I'm ashamed of it. But one day I'll come back to it
     * and correct everything. Don't judge me, please.
     */
    @Test
    public void test() {
        WeightedGraph graph = new WeightedGraph();
        Vertex s = new Vertex("s");
        Vertex t = new Vertex("t");
        Vertex x = new Vertex("x");
        Vertex y = new Vertex("y");
        Vertex z = new Vertex("z");
        graph.addVertex(z);
        graph.addVertex(s);
        graph.addVertex(x);
        graph.addVertex(y);
        graph.addVertex(t);

        s.addEdge(t, 10);
        s.addEdge(y, 5);

        t.addEdge(x, 1);
        t.addEdge(y, 2);

        y.addEdge(z, 2);
        y.addEdge(x, 9);
        y.addEdge(t, 3);

        x.addEdge(z, 4);

        z.addEdge(x, 6);
        z.addEdge(s, 7);

        Dijkstra.dijkstra(graph, s); // path from
        Vertex PATHTO = x; // path to

        Stack<Vertex> stack = new Stack<>();
        while (PATHTO != null) {
            stack.push(PATHTO);
            PATHTO = PATHTO.parent;
        }
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " -> ");
        }

        // (y, s) = y -> z -> s
        // (s, x) = s -> y -> t -> x ->
        // (y, x) = y -> t -> x ->
    }
}