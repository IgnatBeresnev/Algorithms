package me.beresnev.algorithms.graphs;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import me.beresnev.datastructures.Graph.Vertex;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 07.03.17.
 */
public class BFS {

    /**
     * The very basic algorithm when working with graphs. You can
     * think if it as if we lit up the root, and the fire spreads
     * across all the nodes, one after another, evenly. That's what
     * the algorithm does. We start with source, look at its
     * neighbours (level 1), then at it's neighbours neighbours (2),
     * and so on. Do so until there are no more nodes left to look at.
     * Graph can be both directed and undirected. We mark the nodes
     * we've looked at (in case 2 nodes point at 1, we don't want to
     * run it twice). We start by marking root at level 0. Then move
     * onto his children with level 1. Takes O(V+E) time and O(V) space.
     * <p>
     * Can be used to find the shortest path from source to  node N.
     * In this case you'd have to store every node's parent, and once
     * you've found node N, you just go back, following the parent trail,
     * all the way to the root. Parent map is already there.
     */
    private BFS() {
    }

    /**
     * Same logic applies. It's just small details of implementation.
     * There can be a different implementation with queue instead of
     * the hashset frontier, and for example with the usage of indexes
     * instead of Vertex objects. Also, in such case, if you don't care
     * about saving levels for each V, you can just use boolean array[]
     * <p>
     * To find the shortest path, add a param vertex you'd search for, and
     * once you've found it, follow the parent trail back to the root.
     * Might want to implement hashing for quicker comparisons of Vs.
     *
     * @return map<Vertex-Level>,
     */
    @SuppressFBWarnings("UC_USELESS_OBJECT") // parent map
    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
    private static Map<Vertex, Integer> bfs(Vertex root) {
        Map<Vertex, Vertex> parent = new java.util.HashMap<>();
        Map<Vertex, Integer> level = new java.util.HashMap<>();
        level.put(root, 0);

        List<Vertex> frontier = new LinkedList<>();
        frontier.add(root);

        int i = 1; // level
        while (!frontier.isEmpty()) {
            List<Vertex> next = new LinkedList<>();
            for (Vertex u : frontier) {
                if (u == null) break;
                for (Vertex v : u.getNeighbours()) {
                    if (!level.containsKey(v)) {
                        level.put(v, i);
                        parent.put(v, u);
                        next.add(v);
                    }
                }
            }
            frontier = next;
            i++;
        }
        return level;
    }
}
