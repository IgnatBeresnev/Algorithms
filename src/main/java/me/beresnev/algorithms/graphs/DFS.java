package me.beresnev.algorithms.graphs;

import me.beresnev.datastructures.Graph;
import me.beresnev.datastructures.Graph.Vertex;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 07.03.17.
 */
public class DFS {

    /**
     * Depth-First-Search. It's like exploring a maze.
     * You have multiple routes to choose from. You choose one, and
     * follow it until you hit a wall. Return to the original position,
     * choose another root and do the same until you hit the wall. Return,
     * and recursively explore the whole maze. Sooner or later you'll
     * get to the exit. But be careful, and mark routes you've already
     * taken, otherwise you'll be walking in circles or waste time.
     * <p>
     * As the name might suggest, we start with with a vertex and recurse
     * DEEP into its children's children, until there's nothing to explore.
     * So we're doing the DEPTH search. O(V+E) time.
     * <p>
     * If BFS explores all vertices available from given vertex, DFS
     * explores all possible vertices in the graph and their
     * neighbourhood. By the end of the search, we'll have explored everything.
     * <p>
     * Along the way we might use timestamps, which are stored in a map.
     * One stamp for when the V is about to be explored, and one for when
     * we finish exploring all of its children (so we're exiting V).
     * Timestamps make a balanced "parenthesis structure", like ((())),
     * which means we we don't close the parenthesis until we explore everything
     * inside of it, that is the children. And we will close it only when we close
     * the parenthesises of all of the children.
     * <p>
     * DFS Recursion makes what is called a depth-first forest. And, using the
     * time stamps, we distinguish multiple variations of edges. They go:
     * - Tree edge. When we discover a NEW vertex via this edge.
     * - Back edge. When the edge leads back to ancestor. If the graph has
     * a cycle, then there's a back edge.
     * - Forward edge. It's not a tree edge, but it connects V1 to V2, where
     * V2 is the descendant of V1. Basically, a shorter path than tree edge.
     * - Cross edge. The rest. They might connect Vs that are from different
     * trees or Vs where neither is the ancestor.
     */
    private DFS() {
    }

    public static DFSResult DFS(Graph g) {
        DFSResult result = new DFSResult();
        for (Vertex v : g.getVertices()) {
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

        for (Vertex n : v.getNeighbours()) {
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

    public static class DFSResult {
        public List<Vertex> order = new LinkedList<>(); // for topological sort
        public Map<String, Type> edgeType = new HashMap<>(); // determines edge's type
        private Map<Vertex, Vertex> parent = new HashMap<>(); // pretty much "visited"
        private int time = 0; // keeping track of iterations, for edge type
        private Map<Vertex, Integer> startTime = new HashMap<>(); // for edge type
        private Map<Vertex, Integer> finishTime = new HashMap<>(); // for edge type

        private void addEdgeType(Vertex a, Vertex b, Type type) {
            if (a == null)
                a = new Vertex("Null");
            else if (b == null)
                b = new Vertex("Null");
            edgeType.put((a.getLabel() + b.getLabel()).toUpperCase(), type);
        }

        enum Type {
            TREE, BACK, FORWARD, CROSS
        }
    }
}
