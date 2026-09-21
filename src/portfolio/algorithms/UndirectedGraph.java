package portfolio.algorithms;

public class UndirectedGraph extends Graph {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 4895912736L;

    public UndirectedGraph() {
        super();
    }

    @Override
    public boolean addEdge(String node1, String node2) {
        if (node1.equals(node2)) {
            System.err.println(
                "Self edges not permitted in this graph implementation: ("
                + node1 + " -> " + node2 + ") rejected");
            return false;
        }

        addNode(node1); // only adds if node not already in graph.
        addNode(node2);
        boolean addEdgeSuccess = addEdgeFromTo(node1, node2)
            && addEdgeFromTo(node2, node1);

        if (addEdgeSuccess)
            numEdges++;

        return addEdgeSuccess;
    }

    @Override
    public boolean removeEdge(String node1, String node2) {
        if (!containsNode(node1) || !containsNode(node2))
            return false;

        boolean removeEdgeSuccess = removeEdgeFromTo(node1, node2)
            && removeEdgeFromTo(node2, node1);

        if (removeEdgeSuccess)
            numEdges--;

        return removeEdgeSuccess;
    }
}
