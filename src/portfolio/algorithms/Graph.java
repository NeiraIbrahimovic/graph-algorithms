package portfolio.algorithms;

import java.util.*;

public abstract class Graph {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 61839462190L;

    protected Map<String, Set<String>> adjacencySets;
    protected int numNodes;
    protected int numEdges;

    public abstract boolean addEdge(String node1, String node2);

    public abstract boolean removeEdge(String node1, String node2);

    public Graph() {
        adjacencySets = new HashMap<>();
        numNodes = 0;
        numEdges = 0;
    }

    public boolean addNode(String newNode) {
        if (newNode == null || containsNode(newNode))
            return false;
        adjacencySets.put(newNode, new HashSet<>());
        numNodes++;
        return true;
    }

    public Set<String> getNodeNeighbors(String node) {
        return adjacencySets.get(node);
    }

    /* Note: assumes that graph contains given nodes */
    protected boolean addEdgeFromTo(String source, String destination) {
        return adjacencySets.get(source).add(destination);
    }

    /* Note: assumes that graph contains given nodes */
    protected boolean removeEdgeFromTo(String source, String destination) {
        return adjacencySets.get(source).remove(destination);
    }

    public int getNumNodes() {
        return numNodes;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public Set<String> getAllNodes() {
        return adjacencySets.keySet();
    }

    public boolean containsNode(String node) {
        return adjacencySets.containsKey(node);
    }

    /* Note that there is no way to undo this! */
    protected void makeUnmodifiable() {
        adjacencySets.replaceAll((key, value) -> Collections.unmodifiableSet(value));
        adjacencySets = Collections.unmodifiableMap(this.adjacencySets);
    }

    /*
     * Breadth-First Search implementation using iteration
     */
    public boolean bfs(String start, String elementToFind) {
        if (!containsNode(start))
            return false;
        if (start.equals(elementToFind))
            return true;

        Set<String> marked = new HashSet<>();
        Queue<String> toExplore = new LinkedList<>();
        marked.add(start);
        toExplore.add(start);

        while (!toExplore.isEmpty()) {
            String current = toExplore.remove();
            for (String neighbor : getNodeNeighbors(current)) {
                if (!marked.contains(neighbor)) {
                    if (neighbor.equals(elementToFind))
                        return true;
                    marked.add(neighbor);
                    toExplore.add(neighbor);
                }
            }
        }

        return false;
    }

    /*
     * Depth-First Search implementation using recursion
     */
    public boolean dfs(String start, String elementToFind) {
        if (!containsNode(start))
            return false;

        Set<String> marked = new HashSet<>();
        return doDfs(start, elementToFind, marked);
    }

    private boolean doDfs(String start, String elementToFind, Set<String> marked) {
        if (start.equals(elementToFind))
            return true;

        marked.add(start);
        for (String neighbor : getNodeNeighbors(start)) {
            if (!marked.contains(neighbor) &&
                doDfs(neighbor, elementToFind, marked))
                return true;
        }

        return false;
    }
}
