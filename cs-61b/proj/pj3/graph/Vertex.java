/* Vertex.java */

package graph;

import list.DList;
import list.DListNode;

class Vertex {
	private Object name;
	private DList adjList;

	protected Vertex(Object obj) {
		name = obj;
		adjList = new DList();
	}

	protected DListNode addEdge(Vertex vertex) {
		return adjList.insertReturn(vertex);
	}
	
	public Object name() {
		return name;
	}
	
	public DList adjList() {
		return adjList;
	}
	
}
	
	