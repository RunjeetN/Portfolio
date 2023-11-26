package ngordnet.main.Tests;

import ngordnet.main.Graph;
import org.junit.Test;

public class GraphTests {
    @Test
    public void addEdgeTest(){
        Graph g = new Graph();
        g.addEdge("0","1");
        g.addEdge("1","2");
        g.addEdge("3","4");
        g.addEdge("5", "6");
        g.addEdge("5","7");
        g.addEdge("8","10");
        g.addEdge("9","10");
    }
}
