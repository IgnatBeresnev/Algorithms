package me.beresnev.algorithms.graphs;

import me.beresnev.datastructures.Graph;
import me.beresnev.datastructures.Graph.Vertex;
import org.junit.Test;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 07.03.17.
 */
public class DFSTest {

    @Test
    public void DFS() throws Exception {
        Graph g = new Graph();
        g.addVertex(new Vertex("a")); // 0
        g.addVertex(new Vertex("b")); // 1
        g.addVertex(new Vertex("e")); // 2
        g.addVertex(new Vertex("d")); // 3
        g.addVertex(new Vertex("c")); // 4
        g.addVertex(new Vertex("f")); // 5
        g.getVertices().get(0).getNeighbours().add(g.getVertices().get(1));
        g.getVertices().get(0).getNeighbours().add(g.getVertices().get(3));
        g.getVertices().get(1).getNeighbours().add(g.getVertices().get(2));
        g.getVertices().get(2).getNeighbours().add(g.getVertices().get(3));
        g.getVertices().get(4).getNeighbours().add(g.getVertices().get(2));
        g.getVertices().get(4).getNeighbours().add(g.getVertices().get(5));
        g.getVertices().get(5).getNeighbours().add(g.getVertices().get(5));
        DFS.DFSResult result = DFS.DFS(g);
        System.out.println(result.edgeType);
        System.out.println(result.order);
//        {FF=BACK, AB=TREE, CE=CROSS, BE=TREE, AD=FORWARD, CF=TREE, ED=TREE}
//        [f, e, d, c, b, a]
    }
}