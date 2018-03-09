package alda.graph;// Ändra inte på paketet

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.*;

public class UndirectedGraphTest {

	private static final String[] STANDARD_NODES = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };

	private UndirectedGraph<String> graph = new MyUndirectedGraph<>();

	private void add(String... nodes) {
		for (String node : nodes) {
			assertTrue("Unable to add node " + node, graph.add(node));
		}
	}

	private void connect(String node1, String node2) {
		assertTrue(graph.connect(node1, node2));
	}

	private void addExampleNodes() {
		add(STANDARD_NODES);
	}

	@Test
	public void testAdd() {
		addExampleNodes();
		assertFalse(graph.add("D"));
		assertFalse(graph.add("J"));
		assertTrue(graph.add("K"));
	}

	/*
	@Test
	public void testTooLowWeight() {
		addExampleNodes();
		assertFalse(graph.connect("A", "B", 0));
		assertFalse(graph.connect("C", "D", -1));
	}*/

	// Nedanstående kod är skriven i ett format för att beskriva grafer som
	// heter dot och kan användas om ni vill ha en bild av den graf som
	// nedanående test använder. Det finns flera program och webbsidor man kan
	// använda för att omvandla koden till en bild, bland annat
	// http://sandbox.kidstrythisathome.com/erdos/

	// Observera dock att vi kommer att köra testfall på andra och betydligt
	// större grafer.

	// @formatter:off
	// graph G {
	// A -- A [label=1]; A -- G [label=3]; G -- B [label=28];
	// B -- F [label=5]; F -- F [label=3]; F -- H [label=1];
	// H -- D [label=1]; H -- I [label=3]; D -- I [label=1];
	// B -- D [label=2]; B -- C [label=3]; C -- D [label=5];
	// E -- C [label=2]; E -- D [label=2]; J -- D [label=5];
	// }
	// @formatter:on

	private void createExampleGraph() {
		addExampleNodes();

		connect("A", "A");
		connect("A", "G");
		connect("G", "B");
		connect("B", "F");
		connect("F", "F");
		connect("F", "H");
		connect("H", "D");
		connect("H", "I");
		connect("D", "I");
		connect("B", "D");
		connect("B", "C");
		connect("C", "D");
		connect("E", "C");
		connect("E", "D");
		connect("J", "D");
	}

	private void testPath(String start, String end, List<String> path) {
		assertEquals(start, path.get(0));
		assertEquals(end, path.get(path.size() - 1));

		String previous = start;
		for (int i = 1; i < path.size(); i++) {
			assertTrue(graph.isConnected(previous, path.get(i)));
			previous = path.get(i);
		}

		Set<String> nodesInPath = new HashSet<>(path);
		assertEquals(path.size(), nodesInPath.size());
	}


	private void testBreadthFirstSearch(String start, String end, int expectedathLength) {
		createExampleGraph();
		List<String> path = graph.breadthFirstSearch(start, end);

		assertEquals(expectedathLength, path.size());

		testPath(start, end, path);
	}

	@Test
	public void testBreadthFirstSearchFromAToJ() {
		testBreadthFirstSearch("A", "J", 5);
	}

	@Test
	public void testBreadthFirstSearchFromJToA() {
		testBreadthFirstSearch("J", "A", 5);
	}

	@Test
	public void testBreadthFirstSearchFromFToE() {
		testBreadthFirstSearch("F", "E", 4);
	}

	@Test
	public void testBreadthFirstSearchToSameNode() {
		for (String node : STANDARD_NODES) {
			graph = new MyUndirectedGraph<>();
			testBreadthFirstSearch(node, node, 1);
		}
	}


	@Test
	public void myTests() {
		// STANDARD_NODES = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
		addExampleNodes();
		assertTrue(graph.add("K"));
		assertFalse(graph.add("A"));
		assertFalse(graph.add("K"));
		assertEquals(11, graph.getNumberOfNodes());

	}

}
