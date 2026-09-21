package portfolio.algorithms;/*
 * I attest that the code in this file is entirely my own except for the starter
 * code provided with the assignment and the following exceptions:
 * <Enter all external resources and collaborations here. Note external code may
 * reduce your score but appropriate citation is required to avoid academic
 * integrity violations. Please see the Course Syllabus as well as the
 * university code of academic integrity:
 * Signed,
 * Author: Neira Ibrahimovic
 * Date: 2025-03-20
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GraphUtils {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 78327812893L;

    /**
     * Given a graph, this method returns the smallest number of edges from the src
     * node to the dest node, or 0 when src = dest, or âˆ’1 for any invalid input.
     * Invalid inputs are defined as: any of graph, src, or dest is null; no path
     * exists from src to dest; any of src or dest do not exist in graph.
     *
     * @param graph directed or undirected graph
     * @param src   source node
     * @param dest  destination node
     * @return the smallest number of edges from the src to dest, or -1 for any
     *         invalid input
     */
    public static int minDistance(Graph graph, String src, String dest) {
    	 //Handle invalid inputs
        if (graph == null || src == null || dest == null ||
            !graph.containsNode(src) || !graph.containsNode(dest)) {
            return -1;
        }

        //If source and destination are the same, distance is 0
        if (src.equals(dest)) return 0;

        //Queue for BFS
        Queue<String> toExplore = new LinkedList<>();
        //Map to track distance from src
        Map<String, Integer> distances = new HashMap<>();
        
        //Add the first node to the queue of nodes to explore
        toExplore.add(src);
        
        //Add the distance of the first node to itself (0)
        distances.put(src, 0);
        
        //Begin BFS to find the shortest path
        while (!toExplore.isEmpty()) {
        	//Take the first node from the queue of nodes to explore
            String current = toExplore.poll();
            //Get the current distance to the node you are on
            int currentDist = distances.get(current);
            
            //Traverse neighbors
            for (String neighbor : graph.getNodeNeighbors(current)) {
            	//If you have not already visited that neighbor, add it to the distance map with the correct distance
                if (!distances.containsKey(neighbor)) {
                	 distances.put(neighbor, currentDist + 1);
                	//If destination is found
                    if (neighbor.equals(dest)) {
                    	//Return the current destination + 1 to account for one further node down
                    	return currentDist + 1;
                    }
                    toExplore.add(neighbor);
                }
            }
        }
        
        //If no path exists
        return -1;
        
    }

    /**
     * Given a graph, a src node contained in graph, and a distance of at least 1,
     * this method returns the set of all nodes, excluding src, for which the
     * smallest number of edges from src to each node is less than or equal to
     * distance; null is returned if there is any invalid input. Invalid inputs are
     * defined as: any of graph or src is null; src is not in graph; distance is
     * less than 1.
     *
     * @param graph    directed or undirected graph
     * @param src      source node
     * @param distance maximum distance from source to the nodes to include in
     *                 output set
     * @return set of all nodes, excluding src, for which the smallest number of
     *         edges from src to each node is less than or equal to distance, or
     *         null on invalid input
     */
    public static Set<String> nodesWithinDistance(Graph graph, String src, int distance) {
        
    	 //Handle invalid inputs
        if (graph == null || src == null || !graph.containsNode(src) || distance < 1) {
            return null;
        }
        
        //Create set to store the nodes within the right distance
        Set<String> result = new HashSet<>();
        
        //Create queue for BFS
        Queue<String> toExplore = new LinkedList<>();
        
        //Create map to keep track of distance as you're traversing neighbors
        Map<String, Integer> dist = new HashMap<>();

        //Add the starting node to the queue
        toExplore.add(src);
        
        //Add the starting distance, which is 0
        dist.put(src, 0);
        
        //BFS to find nodes within given distance
        while (!toExplore.isEmpty()) {
        	//Take the first node from the queue of nodes to explore
            String current = toExplore.poll();
            //Get the current distance to the node you are on
            int currentDist = dist.get(current);

            //Stop expanding beyond distance limit
            if (currentDist >= distance) continue;

            //Traverse neighbors
            for (String neighbor : graph.getNodeNeighbors(current)) {
            	//If you have not already visited that neighbor, add it to the distance map with the correct distance
                if (!dist.containsKey(neighbor)) {
                    dist.put(neighbor, currentDist + 1);
                    //If the neighbor is less than or equal to the specific distance, add it to the set of nodes in the result
                    if (currentDist + 1 <= distance) {
                        result.add(neighbor);
                    }
                    //Add the neighbor to the queue of nodes to explore
                    toExplore.add(neighbor);
                }
            }
        }

        //Return the result (this will be empty if no nodes are found)
        return result;
    }

    /**
     * Given a Graph, this method indicates whether the List of node values
     * represents a Hamiltonian Cycle.
     *
     * A Hamiltonian Cycle is a valid path through the graph in which every node
     * in the graph is visited exactly once except for the start and end nodes.
     * The method returns a HamiltonianReport object describing the validity of the
     * Hamiltonian Cycle represented by the input List. For this exercise, a cycle must
     * contain at least 3 nodes.
     *
     * @param g      	The directed or undirected graph to operate on
     * @param values 	The proposed path to test on the graph
     * @return Non-null HamiltonianReport describing if values represent a valid
     * 				 	Hamiltonian cycle of g
     */
    
    public static HamiltonianReport isHamiltonianCycle(Graph g, List<String> values) {
        //Edge Case a: Check for null inputs
        if (g == null || values == null) {
        	//If null inputs exist, return the Null_INPUT status
            return new HamiltonianReport(HamiltonianReport.Status.NULL_INPUT, null);
        }

        //Edge Case b: Ensure graph has at least 3 nodes
        if (g.getNumNodes() < 3) {
        	//If less than 3 nodes, return INVALID_LENGTH status
            return new HamiltonianReport(HamiltonianReport.Status.INVALID_LENGTH, null);
        }

        //Edge Case c: Check if the list forms a valid cycle:
        //If the list of values does not contain a first node
        //that repeats at the end (must be at least 4 values to do this)
        //or if the last value is not equal to the first, return an INVALID_CYCLE status
        if (values.size() < 4 || !values.get(0).equals(values.get(values.size() - 1))) {
            return new HamiltonianReport(HamiltonianReport.Status.INVALID_CYCLE, null);
        }

        //Create a set to keep track of the nodes visited
        Set<String> visited = new HashSet<>();
        
        //Start at the first value in the given list of strings
        String start = values.get(0);

        //Traverse through the path given by the list input
        for (int i = 0; i < values.size() - 1; i++) {
            String current = values.get(i);
            String next = values.get(i + 1);

            //Edge Case a: Ensure node exists in graph
            if (!g.containsNode(current)) {
            	//If node doesn't exist, return INVALID_NODE status
                return new HamiltonianReport(HamiltonianReport.Status.INVALID_NODE, current);
            }

            //Ensure the next node exists
            if (!g.containsNode(next)) {
            	//If next node doesn't exist, return INVALID_NODE status
                return new HamiltonianReport(HamiltonianReport.Status.INVALID_NODE, next);
            }

            //Edge Case b: Ensure the edge exists
            if (!g.getNodeNeighbors(current).contains(next)) {
            	//If the edge doesn't exist, return the INVALID_NODE status
                return new HamiltonianReport(HamiltonianReport.Status.INVALID_NODE, next);
            }

            //Edge Case c: Check if any node in the input list is visited more than once (excluding the first/last) by checking in the visited set
            if (i > 0 && visited.contains(current)) {
                return new HamiltonianReport(HamiltonianReport.Status.INVALID_NODE, current);
            }

            //Prevent the start node from being reused mid-path
            if (i > 0 && current.equals(start)) {
                return new HamiltonianReport(HamiltonianReport.Status.INVALID_NODE, current);
            }

            //Add the current node to the set of visited nodes to keep track of it
            visited.add(current);
        }
        
        //Ensure you have a valid cycle
        if (visited.size() != g.getNumNodes()) {
            return new HamiltonianReport(HamiltonianReport.Status.INVALID_CYCLE, null);
        }
        
        //If all checks passed, it's a valid Hamiltonian cycle. Return a VALID status.
        return new HamiltonianReport(HamiltonianReport.Status.VALID, null);
    }
}
