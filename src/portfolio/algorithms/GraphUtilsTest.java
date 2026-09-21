package portfolio.algorithms;
/*
 ***** Important!  Please Read! *****
 *
 *  - Do NOT remove any of the existing import statements
 *  - Do NOT import additional junit packages 
 *  - You MAY add in other non-junit packages as needed
 * 
 *  - Do NOT remove any of the existing test methods or change their name
 *  - You MAY add additional test methods.  If you do, they should all pass
 * 
 *  - ALL of your assert test cases within each test method MUST pass, otherwise the 
 *        autograder will fail that test method
 *  - You MUST write the required number of assert test cases in each test method,
 *        otherwise the autograder will fail that test method
 *  - You MAY write more than the required number of assert test cases as long as they all pass
 * 
 *  - All of your assert test cases within a method must be related to the method they are meant to test
 *  - All of your assert test cases within a method must be distinct and non-trivial
 *  - Your test cases should reflect the method requirements in the homework instruction specification
 * 
 *  - Your assert test cases will be reviewed by the course instructors and they may take off
 *        points if your assert test cases to do not meet the requirements
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.*;

class GraphUtilsTest {
	
	//Initialize a directed graph and undirected graph
    private DirectedGraph directedGraph;
    private UndirectedGraph undirectedGraph;

	@BeforeEach
	public void setUp() throws Exception {
	
		//Create a directed graph
        directedGraph = new DirectedGraph();
        directedGraph.addEdge("A", "B");
        directedGraph.addEdge("B", "C");
        directedGraph.addEdge("C", "D");
        directedGraph.addEdge("D", "A");
        directedGraph.addEdge("B", "D");

        //Create an undirected graph
        undirectedGraph = new UndirectedGraph();
        undirectedGraph.addEdge("A", "B");
        undirectedGraph.addEdge("B", "C");
        undirectedGraph.addEdge("C", "D");
        undirectedGraph.addEdge("D", "A");
	}

	@BeforeAll
	public static void setUpAll() throws Exception {
		// Do not delete this method! You can leave it empty if not used.
		// TODO Write code to set up your test
	}
	
	@Test
	void testMinDistance() {
		/* 
		 * TODO Write at least 5 assert test cases that test your 'minDistance' method
		 * Review the homework instructions and write assert test realated the this methods specification
		 * All 5 assert statements MUST pass.
		 */
		
        //TEST 1: Single-step path
        assertEquals(1, GraphUtils.minDistance(directedGraph, "A", "B"));

        //TEST 2: Multi-step path
        assertEquals(2, GraphUtils.minDistance(directedGraph, "A", "C"));

        //TEST 3: src == dest
        assertEquals(0, GraphUtils.minDistance(directedGraph, "A", "A"));

        //TEST 4: Node doesn't exist
        assertEquals(-1, GraphUtils.minDistance(directedGraph, "A", "Z"));

        //TEST 5: No path exists
        DirectedGraph g2 = new DirectedGraph();
        g2.addEdge("X", "Y");
        g2.addNode("Z");
        assertEquals(-1, GraphUtils.minDistance(g2, "X", "Z"));

        //TEST 6: Undirected graph path
        assertEquals(2, GraphUtils.minDistance(undirectedGraph, "A", "C"));
	}

	@Test
	void testNodesWithinDistance() {
		/* 
		 * TODO Write at least 5 assert test cases that test your 'nodesWithinDistance' method
		 * Review the homework instructions and write assert test realated the this methods specification
		 * All 5 assert statements MUST pass.
		 */
		
        //TEST 1: Normal case
        Set<String> expected1 = new HashSet<>(Arrays.asList("B", "C", "D"));
        assertEquals(expected1, GraphUtils.nodesWithinDistance(directedGraph, "A", 2));

        //TEST 2: Distance = 1
        Set<String> expected2 = new HashSet<>(Set.of("B"));
        assertEquals(expected2, GraphUtils.nodesWithinDistance(directedGraph, "A", 1));

        //TEST 3: Distance too small
        assertNull(GraphUtils.nodesWithinDistance(directedGraph, "A", 0));

        //TEST 4: Source not in graph
        assertNull(GraphUtils.nodesWithinDistance(directedGraph, "Z", 2));

        //TEST 5: No nodes within distance
        DirectedGraph g3 = new DirectedGraph();
        g3.addEdge("M", "N");
        Set<String> empty = new HashSet<>();
        assertEquals(empty, GraphUtils.nodesWithinDistance(g3, "N", 2));

        //TEST 6: Undirected graph test
        Set<String> expected3 = new HashSet<>(Arrays.asList("B", "D"));
        assertEquals(expected3, GraphUtils.nodesWithinDistance(undirectedGraph, "A", 1));
	}

	@Test
	void testIsHamiltonianCycle() {
		/* 
		 * TODO Write at least 5 assert test cases that test your 'isHamiltonianCycle' method
		 * Review the homework instructions and write assert test realated the this methods specification
		 * All 5 assert statements MUST pass.
		 */
		
        //TEST 1: Valid Hamiltonian cycle
        List<String> cycle = List.of("A", "B", "C", "D", "A");
        HamiltonianReport expected = new HamiltonianReport(HamiltonianReport.Status.VALID, null);
        assertEquals(expected, GraphUtils.isHamiltonianCycle(directedGraph, cycle));

        //TEST 2: Null input
        assertEquals(new HamiltonianReport(HamiltonianReport.Status.NULL_INPUT, null), GraphUtils.isHamiltonianCycle(null, null));

        //TEST 3: Too few nodes
        DirectedGraph small = new DirectedGraph();
        small.addEdge("X", "Y");
        assertEquals(new HamiltonianReport(HamiltonianReport.Status.INVALID_LENGTH, null), GraphUtils.isHamiltonianCycle(small, List.of("X", "Y", "X")));

        //TEST 4: Not a cycle (doesn't return to start)
        List<String> notCycle = List.of("A", "B", "C", "D");
        assertEquals(new HamiltonianReport(HamiltonianReport.Status.INVALID_CYCLE, null), GraphUtils.isHamiltonianCycle(directedGraph, notCycle));

        //TEST 5: Repeats node in middle
        List<String> badCycle = List.of("A", "B", "C", "B", "A");
        assertEquals(new HamiltonianReport(HamiltonianReport.Status.INVALID_NODE, "B"), GraphUtils.isHamiltonianCycle(directedGraph, badCycle));

        //TEST 6: Valid Hamiltonian cycle on undirected graph
        List<String> undirectedCycle = List.of("A", "B", "C", "D", "A");
        assertEquals(new HamiltonianReport(HamiltonianReport.Status.VALID, null), GraphUtils.isHamiltonianCycle(undirectedGraph, undirectedCycle));
	}
	
    @Test
    void testWithStudentGraphFile() throws IOException {
        //Load graph from student_graph_test.txt using GraphBuilder
        Graph studentGraph = GraphBuilder.buildDirectedGraph("src/portfolio/algorithms/student_graph_test.txt");

        //TEST 1: Distance between nodes
        int dist = GraphUtils.minDistance(studentGraph, "0", "2");
        assertTrue(dist >= 0); //assuming there's a valid path
        assertEquals(1, dist);

        //TEST 2: Nodes within distance
        Set<String> result = new HashSet<>(Arrays.asList("1", "2", "3"));
        assertEquals(result, GraphUtils.nodesWithinDistance(studentGraph, "0", 2));

        //TEST 3: Hamiltonian cycle check with placeholder list (update with real test case)
        List<String> path = List.of("0", "1", "2", "3", "0");
        assertEquals(new HamiltonianReport(HamiltonianReport.Status.VALID, null), GraphUtils.isHamiltonianCycle(studentGraph, path));
    }
	
	
}
