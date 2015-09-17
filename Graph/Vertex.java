import java.util.HashMap;
import java.util.HashSet;

public class Vertex {
	public int ID;
	public String name;
	public HashSet<String> neighbors;

	public Vertex(String n){
		name = n;
	}
	
	public Vertex(int id, String n, HashSet<String> e) {
		ID = id;
		name = n;
		neighbors = e; 
	}
}