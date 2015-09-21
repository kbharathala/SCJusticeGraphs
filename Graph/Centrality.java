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
					if(!labels.get(j).equals(justice)&& g.getEdgeWeight(labels.get(j),justice)== -1){
						g.addEdge(justice,labels.get(j),new Integer(s[j]));
					}
				}
				i++;
			}
			//g.printString();
			HashMap<String, Double> centrality_measures = laplacianCentrality(g); 
			for(String s: centrality_measures.keySet())
				System.out.println(s + " " + centrality_measures.get(s));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static HashMap<String, Double> laplacianCentrality(Graph g) {
		HashMap<String, Double> centralities = new HashMap<String, Double>();
		double whole = laplacianEnergy(g);
		for(String s: g.getVertexIds()){
			Graph minus = new Graph(g);
			minus.removeVertex(s);
			centralities.put(s, (whole-laplacianEnergy(minus))/whole);
		}
		return centralities;
	}
	
	public static double laplacianEnergy(Graph g) {
		int [][] w = new int[g.getVertexIds().size()][g.getVertexIds().size()];
		for(String a: g.getVertexIds()){
			for(String b: g.getVertexIds()){
				if(a!= null && b!=null){
					if(!a.equals(b)){
						if(g.getEdgeWeight(a,b)== -1)
							w[g.Vertexes.get(a)][g.Vertexes.get(b)] = g.getEdgeWeight(b,a);
						else 
							w[g.Vertexes.get(a)][g.Vertexes.get(b)] = g.getEdgeWeight(a,b);
					}
					else{
						w[g.Vertexes.get(a)][g.Vertexes.get(b)] = 0;
					}
				}
			}
		}
		for(String a: g.getVertexIds()){
			for(String b: g.getVertexIds()){
				if(a.equals(b)){
					int sum = 0; 
					for(int j=0; j<w[0].length;j++){
						sum += w[g.Vertexes.get(a)][j];
					}
					w[g.Vertexes.get(a)][g.Vertexes.get(b)] = sum *-1;
				}
			}	
		}
		double energy=0; 
		for(String a: g.getVertexIds()){
			for(String b: g.getVertexIds()){
				if(!a.equals(b)){
					energy += 2*(double)w[g.Vertexes.get(a)][g.Vertexes.get(b)] * w[g.Vertexes.get(a)][g.Vertexes.get(b)];
				}
				else{
					energy += 
							w[g.Vertexes.get(a)][g.Vertexes.get(b)] * w[g.Vertexes.get(a)][g.Vertexes.get(b)];
				}
			}
		}
		return energy;
	}
}