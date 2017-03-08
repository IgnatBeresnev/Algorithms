package me.beresnev.algorithms.sorting;

import me.beresnev.algorithms.DFS;
import me.beresnev.datastructures.Graph;

import java.util.Collections;
import java.util.List;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 08.03.17.
 */
public class TopologicalSort {

    /**
     * To do perform topological search on a graph,
     * it has to be DAG (Directed Acyclic Graph).
     * Time = DAG - O(V+E)
     * <p>
     * Imagine we have a DAG that describes the logic of
     * dressing up in the morning. Some things need to be
     * put on before the others. For example, socks before
     * the boots or trousers before the pants. However, we
     * don't care when we put our watches on. Topological
     * sort performed on such a graph would give us the correct
     * order in which to do things in the graph. We use DFS
     * to achieve the result.
     */
    private TopologicalSort() {
    }

    /**
     * DFS guarantees that 1) we will explore all vertices;
     * 2) In a graph v1-v2-v3, we will finish v3 before v2,
     * and v2 before v1. That's the perfect fit for this sort.
     * <p>
     * In the DFS, we recurse deep into a vertex's children,
     * and when we start unfolding, we put items in a list
     * called order. In other words, first we discover all
     * children, and then go back to the parent. If we apply
     * the example with the morning routine, where v1 is the
     * act of putting your socks on, and v2 is the boots,
     * we go to v1, then to v2. put v2 into order, go back to
     * v1, put v1 into order. That's the opposite of what
     * we want, so we just reverse it, and have the result.
     */
    public static List<Graph.Vertex> sort(Graph g) {
        DFS.DFSResult result = DFS.DFS(g);
        Collections.reverse(result.order);
        return result.order;
    }
}
