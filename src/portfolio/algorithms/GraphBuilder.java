package portfolio.algorithms;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class GraphBuilder implements Serializable {
    @Serial
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 27548990124L;

    public static DirectedGraph buildDirectedGraph(String filename) {
        DirectedGraph dg = new DirectedGraph();
        buildGraph(dg, filename);
        return dg;
    }

    public static UndirectedGraph buildUndirectedGraph(String filename) {
        UndirectedGraph ug = new UndirectedGraph();
        buildGraph(ug, filename);
        return ug;
    }

    protected static void buildGraph(Graph graph, String filename) {
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            lines.map(line -> line.split(" "))
                .filter(edge -> edge.length >= 2)
                .forEach(edge -> graph.addEdge(edge[0], edge[1]));
        } catch (IOException e) {
            System.out.println("Error when trying to read " + filename + ": " + e);
        }
    }
}
