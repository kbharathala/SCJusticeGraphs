
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph {
	private HashMap<String, HashMap<String, Integer>> adjacencylist;
	private HashSet<Vertex> Vertexes;

	public Graph() {
		adjacencylist = new HashMap<String, HashMap<String, Integer>>();
		Vertexes = new HashSet<Vertex>();
	}

	public Graph(Graph g) {
		adjacencylist = new HashMap<String, HashMap<String, Integer>>(
				g.adjacencylist);
		Vertexes = g.Vertexes;
	}

	public void printString() {
		for (String s : adjacencylist.keySet()) {
			if(adjacencylist.get(s) == null)
				System.out.println("none");
			else{
				System.out.println(s + "--->");
				for (String n : adjacencylist.get(s).keySet()) {
					System.out.println(s + "<-->" + n + " " + adjacencylist.get(s).get(n));
				}
			}
		}
	}

	public void addEdge(String u, String v, int w) {
		adjacencylist.get(u).put(v, w);
		adjacencylist.get(v).put(u, w);
	}
	
	public boolean addVertex(Vertex u) {
		if (adjacencylist.containsKey(u)) {
			return false;
		}
		adjacencylist.put(u.name, new HashMap<String, Integer>());
		Vertexes.add(u);
		return true;
	}

	public Set<String> getVertexIds() {
		return adjacencylist.keySet();
	}

	public Set<String> getNeighbors(String s) {
		return adjacencylist.get(s).keySet();
	}
	
	public int getEdgeWeight(String u, String v) {
		if(adjacencylist.get(u).get(v) != null)
			return adjacencylist.get(u).get(v);
		else 
			return -1;
	}

}