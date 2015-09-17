import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Centrality{
	public static void main(String [] args){
		Scanner reader;
		try {
			// read in adjacency matrix of weights
			reader = new Scanner(new File("C:/2014.csv"));
			String [] justices = reader.next().split(",");
			Graph g = new Graph();
			HashMap<Integer,String> labels 
							= new HashMap<Integer,String>();
			for(int i = 1; i<justices.length; i++){
				labels.put(i, justices[i]);
				g.addVertex(new Vertex(justices[i]));
			}
			int i = 1;
			while(reader.hasNext()){
				String [] s = reader.next().split(",");
				String justice = s[0];
				for(int j=1; j<s.length; j++){
					if(!labels.get(j).equals(justice)){
						g.addEdge(justice,labels.get(j),new Integer(s[j]));
					}
				}
				i++;
			}
			//g.printString();
			HashMap<String, Double> centrality_measures = centrality(g); 
			for(String s: centrality_measures.keySet())
				System.out.println(centrality_measures.get(s));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static HashMap<String, Double> centrality(Graph g) {
		if (g == null) {
			throw new IllegalArgumentException();
		}
		HashMap<String, Double> cb = new HashMap<String, Double>();
		LinkedList Q = new LinkedList();
		HashMap<String, ArrayList> p = new HashMap<String, ArrayList>();
		HashMap<String, Double> sigma = new HashMap<String, Double>();
		HashMap<String, Double> d = new HashMap<String, Double>();
		for (String u : g.getVertexIds()) {
			cb.put(u, 0.0);
		}
		for (String user : g.getVertexIds()) {
			Q = new LinkedList();
			Stack s = new Stack();
			for (String u : g.getVertexIds()) {
				sigma.put(u, 0.0);
				d.put(u, (-1.0));
				p.put(u, new ArrayList());
			}
			sigma.put(user, 1.0);
			d.put(user, 0.0);
			Q.add(user);
			while (!Q.isEmpty()) {
				String v = (String) Q.poll();
				s.push(v);
				for (String w : g.getNeighbors(v)) {
					if (d.get(w) < 0) {
						Q.add(w);
						d.put(w, d.get(v) + 1);
					}
					if (d.get(w).equals(d.get(v) + 1)) {
						sigma.put(w, sigma.get(w) + sigma.get(v));
						p.get(w).add(v);
					}
				}
			}
			HashMap<String, Double> delta = new HashMap<String, Double>();
			for (String u : g.getVertexIds()) {
				delta.put(u, 0.0);
			}
			while (!s.isEmpty()) {
				System.out.println(user + "-->");
				String w = (String) s.pop();
				for (Object v : p.get(w)) {
					delta.put((String) v,
							delta.get(v)
									+ (((double) sigma.get(v)) / ((double) sigma
											.get(w)))
									* (1 + 1.0 * delta.get(w)));
					if (!w.equals(user)) {
						cb.put(w, cb.get(w) + delta.get(w));
						System.out.println("hey" + delta.get(w));
					}
				}
			}
		}
		return cb;
	}

}