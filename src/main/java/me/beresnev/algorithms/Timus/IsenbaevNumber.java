package me.beresnev.algorithms.Timus;

import me.beresnev.algorithms.graphs.BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 08.03.17.
 */
public class IsenbaevNumber {
    /**
     * This solve method solves the "Isenbaev number" problem found
     * on Timus, using BFS: http://acm.timus.ru/problem.aspx?space=1&num=1837
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
     * @see BFS for details
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
}
