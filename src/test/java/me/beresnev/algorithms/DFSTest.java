package me.beresnev.algorithms;

import org.junit.Test;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 07.03.17.
 */
public class DFSTest {

    @Test
    public void DFS() throws Exception {
        DFS.Graph g = new DFS.Graph();
        g.vertices.add(new DFS.Vertex("a")); // 0
        g.vertices.add(new DFS.Vertex("b")); // 1
        g.vertices.add(new DFS.Vertex("e")); // 2
        g.vertices.add(new DFS.Vertex("d")); // 3
        g.vertices.add(new DFS.Vertex("c")); // 4
        g.vertices.add(new DFS.Vertex("f")); // 5
        g.vertices.get(0).neighbours.add(g.vertices.get(1));
        g.vertices.get(0).neighbours.add(g.vertices.get(3));
        g.vertices.get(1).neighbours.add(g.vertices.get(2));
        g.vertices.get(2).neighbours.add(g.vertices.get(3));
        g.vertices.get(4).neighbours.add(g.vertices.get(2));
        g.vertices.get(4).neighbours.add(g.vertices.get(5));
        g.vertices.get(5).neighbours.add(g.vertices.get(5));
        DFS.DFSResult result = DFS.DFS(g);
        System.out.println(result.edgeType);
        System.out.println(result.order);
//        {FF=BACK, AB=TREE, CE=CROSS, BE=TREE, AD=FORWARD, CF=TREE, ED=TREE}
//        [f, e, d, c, b, a]
    }
}