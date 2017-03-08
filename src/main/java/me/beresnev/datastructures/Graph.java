package me.beresnev.datastructures;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 06.03.17.
 */
public class Graph {
    private List<Vertex> vertices = new ArrayList<>();

    /**
     * Graph - set of V (vertices) and E (edges), where an edge
     * is represented as (v1, v2) - it's the line between Vs.
     * Graphs can be directed (that is edges are ordered), in which
     * case we mark them as (v1, v2) - there's 1 arrow from v1 -> v2,
     * and undirected (edges unordered), we write them as {v1, v2},
     * here it's the two-way line v1 <--> v2 (mutual), in this case
     * the order in which you write them doesn't matter (can be {v2, v1}).
     * Examples: social network - undirected, "friendship" is mutual.
     * Web-crawlers - directed, since website 1 can point to website 2,
     * and website 2 may not point back to website 1. So it's 1 way.
     * Edges can have "weight". For example, if we draw our country as
     * a graph, where edges == roads, an edge's weight will be the distance
     * in km. It'll help us find the shortest path, for example.
     * <p>
     * Graph representation:
     * - Matrix. if [i][j] == 1, then there's an edge between i and j.
     * Advantage: time complexity when want to know if there's an edge
     * between two Vs. Access by index is constant.
     * Disadvantage: space complexity is v^2. Also, if you want to find
     * two adjacent nodes, it'll take a shitload of time. OK for dense.
     * If graph is undirected, then we can save some space by storing
     * only upper value for an edge (if [v1,v2] == 1, then we accept
     * by default that in undirected [v2, v1] will also be 1, but there's
     * no need to explicitly say that). Also, we can store weight instead of 1.
     * - Adjacency list. Basically each V has a list of it's neighbours
     * or edges (if you want to store weight, for example). Less space
     * consumption O(V+E), but a slightly worse performance when look up
     * an edge. If neighbours are ordered, we can use binary search
     * for O(logN) time, otherwise linear. Best for scarce graphs.
     * Also, you can do implicit graphs - that is compute local structure
     * on the fly. Technically takes "zero" space. Like solving rubik's cube.
     * <p>
     * Properties:
     * - Self-loop. When a V points to itself. Website links to itself.
     * - Multi(Parallel)-edge. v1 and v2 may have different routes with diff. weight.
     * - Degree. Number of edges coming from a V. In undir graph, edges*2=all degrees,
     * for dir. graphs - out-degrees (N going out) and in-degrees (has N going in).
     * Sum(in) + sum(out) = 2x #edges. If sum(out) >, then black-hole, don't come back.
     * - Dense. When #edges is very close to possible max. number of edges.
     * - Scarce. Opposite. When #edges is significantly < than max edges.
     * - Path. Sequence of Vs that are connected by edges.
     * - Simple path. == Path, but no Vs are repeated. Usually default.
     * - Closed walk. When it starts and ends at the same V.
     * - Trail. No edges are repeated.
     * - Weakly connected. a->b; a->c; c->b
     * - Connected. Average graph a<->b<->c<->d<->e
     * - Strongly connected. There's a path from any V to any V.
     * - Walk length == number of edges.
     * - Acyclic graph - has no graph cycles, basically a tree.
     */
    public Graph() {
    }

    public void addVertex(Vertex a) {
        vertices.add(a);
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    /**
     * Simple undirected and unweighted vertex
     * in an average Adjacency-list representation.
     * If you want to make it directed, just
     * change the addNeighbour logic.
     *
     * Also, this class uses set because, I think,
     * there's no point in having a linked list,
     * which would allow multiple copies of the same
     * Vertex, in an unweighted graph.
     *
     * One more thing - you might want to postpone
     * the neighbours initialization until adding
     * the first neighbour. That would save space.
     *
     * @see Edge for how to make edges weighted
     */
    public static class Vertex {
        private Set<Vertex> neighbours;
        private String label;

        public Vertex(String label) {
            neighbours = new HashSet<>();
            this.label = label;
        }

        public void addNeighbour(Vertex a) {
            neighbours.add(a);
            a.neighbours.add(this);
        }

        public String getLabel() {
            return label;
        }

        public Set<Vertex> getNeighbours() {
            return neighbours;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return label != null ? label.equals(vertex.label) : vertex.label == null;
        }

        @Override
        public int hashCode() {
            return label != null ? label.hashCode() * 31 : 0;
        }

        public String toString() {
            return label;
        }
    }

    /**
     * If you want to make edges weighted, here's the list of changes:
     * - Make neighbours in Vertex a list. We can have multiple edges
     * from one V to another, they might have different weight.
     * - Use Edge as generic in neighbours list. Instead of adding
     * vertices to neighbours, add edges.
     * - Add required getters/setters, generate equals & hashCode
     * - If you want, you can leave only one Vertex inside the class,
     * instead of storing two. That would require some changes in Vertex
     * class and in general logic of the graph as well then.
     */
    @SuppressFBWarnings("URF_UNREAD_FIELD")
    private static class Edge {
        private Vertex one, two;
        private int weight;

        public Edge(Vertex one, Vertex two, int weight) {
            this.one = one;
            this.two = two;
            this.weight = weight;
        }
    }
}
