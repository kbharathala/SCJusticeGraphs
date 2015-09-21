
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph {
	private HashMap<String, HashMap<String, Integer>> adjacencylist;
	public HashMap<String, Integer> Vertexes;
	private int count;

	public Graph() {
		adjacencylist = new HashMap<String, HashMap<String, Integer>>();
		Vertexes = new HashMap<String, Integer>();
		count = 0;
	}

	public Graph(Graph g) {
		adjacencylist = new HashMap<String, HashMap<String, Integer>>(
				g.adjacencylist);
		Vertexes = new HashMap<String, Integer>(g.Vertexes);
		count = g.count;
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
		Vertexes.put(u.name, count);
		count++;
		return true;
	} 
	
	public boolean removeVertex(String u) {
		for(String n: getNeighbors(u))
			adjacencylist.get(n).remove(u);
		adjacencylist.remove(u);
		int adjust = Vertexes.get(u);
		for(String s : Vertexes.keySet())
			if(Vertexes.get(s)>Vertexes.get(u))
				Vertexes.put(s,Vertexes.get(s)-1);
		Vertexes.remove(u); 
		return true;
	}

	public Set<String> getVertexIds() {
		return Vertexes.keySet();
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