package list;

import list.*;

/**
 * This is basically a class to store a node-and-list pair.  The node's item is
 * the name of a vertex, and the list is the adjacency list of that vertex.  This
 * class is implemented to improve the running time of remove() in
 * WUGraph.java, specifically, the removal of a vertex from a graph's list of
 * vertices. 
 */

public class NodeListPair {
	private DListNode me;
	private DList adjList;

	public NodeListPair(DListNode node, DList list) {
		me = node;
		adjList = list;
	}
	
	public DListNode me() {
		return me;
	}
	
	public DList adjList() {
		return adjList;
	}

}