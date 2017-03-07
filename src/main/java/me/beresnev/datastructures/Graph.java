package me.beresnev.datastructures;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 06.03.17.
 */
public class Graph {

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
    private Graph() {
    }

    /**
     * This solve method solves the "Isenbaev number" problem found
     * on Timus, also using BFS: http://acm.timus.ru/problem.aspx?space=1&num=1837
     * It was accepcted by the system, and the total time and space cost
     * are 0.078 and 230kb. First it builds an undirected, unweighted graph, and
     * then uses BFS algorithm to get levels of all the nodes, which == result.
     * <p>
     * The input is in the resources with file name "Isenbaev input"
     */
    private static void solve() throws IOException {
        TreeMap<String, Vertex> graph = buildGraph();
        Map<Vertex, Integer> levels = bfsLevels(graph.get("Isenbaev"));

        for (Map.Entry<String, Vertex> entry : graph.entrySet()) {
            Vertex value = entry.getValue();
            if (!levels.containsKey(value))
                System.out.println(entry.getKey() + " undefined");
            else System.out.println(entry.getKey() + " " + levels.get(value));
        }
    }

    /**
     * @see #solve() for details
     */
    private static TreeMap<String, Vertex> buildGraph() throws IOException {
        TreeMap<String, Vertex> vertexMap = new java.util.TreeMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "ISO-8859-1"));

        int times = Integer.parseInt(reader.readLine());

        String line;
        String[] surnames;
        for (int j = 0; j < times; j++) {
            line = reader.readLine();
            if (line == null || line.isEmpty()) continue;
            surnames = line.split("\\s+");
            for (String surname : surnames) {
                if (!vertexMap.containsKey(surname)) {
                    Vertex newVertex = new Vertex(surname);
                    vertexMap.put(surname, newVertex);
                }
            }
            // I know there'll be 3 names only, so we need 1+2, 2+3, 3+1
            for (int i = 0; i < surnames.length; i++) {
                int second = i == 2 ? 0 : i + 1;
                addEdge(vertexMap.get(surnames[i]), vertexMap.get(surnames[second]));
            }
        }
        return vertexMap;
    }

    /**
     * @see me.beresnev.algorithms.BFS for details
     */
    private static Map<Vertex, Integer> bfsLevels(Vertex Isenbaev) {
        Map<Vertex, Integer> level = new java.util.HashMap<>();
        level.put(Isenbaev, 0);

        Set<Vertex> frontier = new HashSet<>();
        frontier.add(Isenbaev);

        int i = 1;
        while (!frontier.isEmpty()) {
            Set<Vertex> next = new HashSet<>();
            for (Vertex u : frontier) {
                if (u == null) break;
                for (Vertex v : u.neighbours) {
                    if (!level.containsKey(v)) {
                        level.put(v, i);
                        next.add(v);
                    }
                }
            }
            frontier = next;
            i++;
        }
        return level;
    }

    /**
     * For undirected graphs. If A -> B, then B -> A
     */
    private static void addEdge(Vertex one, Vertex two) {
        one.neighbours.add(two);
        two.neighbours.add(one);
    }

    private static class Vertex {
        Set<Vertex> neighbours;
        String label;

        Vertex(String label) {
            neighbours = new HashSet<>();
            this.label = label;
        }

        public String toString() {
            return label;
        }
    }

    /**
     * If you want to add weight, then an object Edge might solve your problem.
     * You'd have to restructure Vertex's neighbours set a little bit though.
     * This class is not used in the problem solving, it's just for demonstration.
     */
    @SuppressFBWarnings({"UUF_UNUSED_FIELD", "URF_UNREAD_FIELD"})
    private static class Edge {
        Vertex one, two;
        int weight;

        public Edge(Vertex one, Vertex two) {
            this.one = one;
            this.two = two;
        }
    }
}
