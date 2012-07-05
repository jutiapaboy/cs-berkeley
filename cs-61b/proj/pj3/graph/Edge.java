package graph;

import list.DListNode;


public class Edge implements Comparable{
	
	protected int weight;
	protected DListNode node1;
	protected DListNode node2;
	
	public Edge(int w, DListNode d1, DListNode d2) {
		weight = w;
		node1 = d1;
		node2 = d2;
	}
	
	// MIGHT NOT NEED
	public int compareTo(Object obj) {
		Edge e = (Edge) obj;		// assume we only compare Edges with Edges
		if (weight < e.weight) {
			return -1;
		} else if (weight == e.weight) {
			return 0;
		} else {
			return 1;
		}
	}
	
	
}